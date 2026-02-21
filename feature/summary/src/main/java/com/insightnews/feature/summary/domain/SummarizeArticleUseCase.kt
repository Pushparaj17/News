package com.insightnews.feature.summary.domain

import com.insightnews.core.domain.model.Article
import com.insightnews.core.domain.model.ArticleSummary
import com.insightnews.core.domain.repository.SummaryRepository
import javax.inject.Inject

class SummarizeArticleUseCase @Inject constructor(
    private val summaryRepository: SummaryRepository
) {
    suspend operator fun invoke(article: Article): Result<ArticleSummary> {
        return summaryRepository.summarizeArticle(article)
    }
}
