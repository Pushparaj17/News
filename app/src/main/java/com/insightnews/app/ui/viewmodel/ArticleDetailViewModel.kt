package com.insightnews.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.insightnews.core.domain.model.Article
import com.insightnews.core.domain.repository.BookmarkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleDetailViewModel @Inject constructor(
    private val bookmarkRepository: BookmarkRepository
) : ViewModel() {

    private val _isBookmarked = MutableStateFlow(false)
    val isBookmarked: StateFlow<Boolean> = _isBookmarked.asStateFlow()

    fun loadBookmarkState(articleId: String) {
        viewModelScope.launch {
            _isBookmarked.update { bookmarkRepository.isBookmarked(articleId) }
        }
    }

    fun addBookmark(article: Article) {
        viewModelScope.launch {
            bookmarkRepository.addBookmark(article)
            _isBookmarked.update { true }
        }
    }

    fun removeBookmark(articleId: String) {
        viewModelScope.launch {
            bookmarkRepository.removeBookmark(articleId)
            _isBookmarked.update { false }
        }
    }
}
