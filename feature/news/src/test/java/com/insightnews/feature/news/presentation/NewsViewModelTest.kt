package com.insightnews.feature.news.presentation

import androidx.paging.PagingData
import com.insightnews.core.domain.model.NewsCategory
import com.insightnews.core.domain.repository.NewsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class NewsViewModelTest {

    @Mock
    private lateinit var newsRepository: NewsRepository

    private lateinit var viewModel: NewsViewModel

    @Before
    fun setup() {
        `when`(newsRepository.getTopHeadlines(NewsCategory.TECHNOLOGY))
            .thenReturn(flowOf(PagingData.empty()))
        viewModel = NewsViewModel(newsRepository)
    }

    @Test
    fun `selecting business category updates state`() = runTest(UnconfinedTestDispatcher()) {
        `when`(newsRepository.getTopHeadlines(NewsCategory.BUSINESS))
            .thenReturn(flowOf(PagingData.empty()))

        viewModel.onEvent(NewsUiEvent.SelectCategory(NewsCategory.BUSINESS))

        val state = viewModel.state.value
        assert(state is NewsUiState.Success)
        assert((state as NewsUiState.Success).selectedCategory == NewsCategory.BUSINESS)
    }
}
