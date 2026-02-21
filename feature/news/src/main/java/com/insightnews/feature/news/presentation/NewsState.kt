package com.insightnews.feature.news.presentation

import androidx.paging.PagingData
import com.insightnews.core.domain.model.Article
import com.insightnews.core.domain.model.NewsCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

/**
 * Sealed UI state for News screen - demonstrates senior-level state management.
 */
sealed class NewsUiState {
    data object Loading : NewsUiState()
    data class Success(
        val articles: Flow<PagingData<Article>>,
        val selectedCategory: NewsCategory,
        val searchQuery: String = "",
        val isSearchMode: Boolean = false
    ) : NewsUiState()
    data class Error(val message: String) : NewsUiState()
}

sealed class NewsUiEvent {
    data class SelectCategory(val category: NewsCategory) : NewsUiEvent()
    data class Search(val query: String) : NewsUiEvent()
    data object ClearSearch : NewsUiEvent()
    data object Refresh : NewsUiEvent()
    data class ArticleClick(val article: Article) : NewsUiEvent()
}
