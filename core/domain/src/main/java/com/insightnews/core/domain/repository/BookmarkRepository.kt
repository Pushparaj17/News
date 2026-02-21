package com.insightnews.core.domain.repository

import com.insightnews.core.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {
    fun getBookmarks(): Flow<List<Article>>
    suspend fun isBookmarked(articleId: String): Boolean
    suspend fun addBookmark(article: Article)
    suspend fun removeBookmark(articleId: String)
}
