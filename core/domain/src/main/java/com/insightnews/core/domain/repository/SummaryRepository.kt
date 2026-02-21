package com.insightnews.core.domain.repository

import com.insightnews.core.domain.model.Article
import com.insightnews.core.domain.model.ArticleSummary

interface SummaryRepository {
    suspend fun summarizeArticle(article: Article): Result<ArticleSummary>
}
