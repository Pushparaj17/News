package com.insightnews.feature.summary.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.insightnews.core.domain.model.Article
import com.insightnews.core.domain.model.ArticleSummary
import com.insightnews.feature.summary.domain.SummarizeArticleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class SummaryUiState {
    data object Loading : SummaryUiState()
    data class Success(val summary: ArticleSummary) : SummaryUiState()
    data class Error(val message: String) : SummaryUiState()
}

@HiltViewModel
class SummaryViewModel @Inject constructor(
    private val summarizeArticleUseCase: SummarizeArticleUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<SummaryUiState>(SummaryUiState.Loading)
    val state: StateFlow<SummaryUiState> = _state.asStateFlow()

    fun loadSummary(article: Article) {
        viewModelScope.launch {
            _state.update { SummaryUiState.Loading }
            summarizeArticleUseCase(article)
                .onSuccess { summary ->
                    _state.update { SummaryUiState.Success(summary) }
                }
                .onFailure { error ->
                    _state.update { SummaryUiState.Error(error.message ?: "Failed to summarize") }
                }
        }
    }
}
