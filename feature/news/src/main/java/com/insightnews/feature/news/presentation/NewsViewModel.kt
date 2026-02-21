package com.insightnews.feature.news.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.insightnews.core.domain.model.NewsCategory
import com.insightnews.core.domain.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private val _state = MutableStateFlow<NewsUiState>(NewsUiState.Loading)
    val state: StateFlow<NewsUiState> = _state.asStateFlow()

    private val _events = MutableSharedFlow<NewsUiEvent>(extraBufferCapacity = 1)
    val events: SharedFlow<NewsUiEvent> = _events

    init {
        loadHeadlines(NewsCategory.TECHNOLOGY)
    }

    fun onEvent(event: NewsUiEvent) {
        viewModelScope.launch {
            when (event) {
                is NewsUiEvent.SelectCategory -> {
                    (_state.value as? NewsUiState.Success)?.isSearchMode?.let {
                        if (!it == true) {
                            loadHeadlines(event.category)
                        }
                    }
                }
                is NewsUiEvent.Search -> {
                    if (event.query.isNotBlank()) {
                        loadSearch(event.query)
                    } else {
                        loadHeadlines((_state.value as? NewsUiState.Success)?.selectedCategory ?: NewsCategory.TECHNOLOGY)
                    }
                }
                NewsUiEvent.ClearSearch -> {
                    loadHeadlines((_state.value as? NewsUiState.Success)?.selectedCategory ?: NewsCategory.TECHNOLOGY)
                }
                NewsUiEvent.Refresh -> {
                    val current = _state.value
                    when (current) {
                        is NewsUiState.Success -> {
                            if (current.isSearchMode) {
                                loadSearch(current.searchQuery)
                            } else {
                                loadHeadlines(current.selectedCategory)
                            }
                        }
                        else -> loadHeadlines(NewsCategory.TECHNOLOGY)
                    }
                }
                is NewsUiEvent.ArticleClick -> _events.emit(event)
            }
        }
    }

    private fun loadHeadlines(category: NewsCategory) {
        viewModelScope.launch {
            _state.update {
                NewsUiState.Success(
                    articles = newsRepository.getTopHeadlines(category),
                    selectedCategory = category,
                    searchQuery = "",
                    isSearchMode = false
                )
            }
        }
    }

    private fun loadSearch(query: String) {
        viewModelScope.launch {
            _state.update {
                NewsUiState.Success(
                    articles = newsRepository.searchNews(query),
                    selectedCategory = (it as? NewsUiState.Success)?.selectedCategory ?: NewsCategory.TECHNOLOGY,
                    searchQuery = query,
                    isSearchMode = true
                )
            }
        }
    }
}
