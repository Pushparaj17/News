package com.insightnews.feature.summary.domain

import com.insightnews.core.domain.model.Article
import com.insightnews.core.domain.model.ArticleSummary
import com.insightnews.core.domain.repository.SummaryRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.util.Date

class SummarizeArticleUseCaseTest {

    @Mock
    private lateinit var summaryRepository: SummaryRepository

    private lateinit var useCase: SummarizeArticleUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        useCase = SummarizeArticleUseCase(summaryRepository)
    }

    @Test
    fun `summarizeArticle returns success with valid summary`() = runBlocking {
        val article = createFakeArticle()
        val expectedSummary = ArticleSummary(
            articleId = "1",
            summaryText = "Summary",
            bulletPoints = listOf("Point 1"),
            keywords = listOf("tech"),
            estimatedReadingTimeMinutes = 2,
            topics = listOf("technology")
        )
        `when`(summaryRepository.summarizeArticle(article)).thenReturn(Result.success(expectedSummary))

        val result = useCase(article)

        assertTrue(result.isSuccess)
        assertEquals(expectedSummary, result.getOrNull())
    }

    private fun createFakeArticle() = Article(
        id = "1",
        title = "Test",
        description = "Description",
        content = "Content here with some text for summarization.",
        sourceName = "Source",
        sourceUrl = null,
        author = null,
        url = "https://example.com",
        imageUrl = null,
        publishedAt = Date(),
        category = null,
        language = null,
        country = null
    )
}
