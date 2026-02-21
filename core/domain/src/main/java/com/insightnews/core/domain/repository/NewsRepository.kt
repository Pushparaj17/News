package com.insightnews.core.domain.repository

import androidx.paging.PagingData
import com.insightnews.core.domain.model.Article
import com.insightnews.core.domain.model.NewsCategory
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getTopHeadlines(category: NewsCategory): Flow<PagingData<Article>>
    fun searchNews(query: String): Flow<PagingData<Article>>
    suspend fun getArticleById(id: String): Article?
    suspend fun refreshHeadlines(category: NewsCategory): Result<List<Article>>
}
