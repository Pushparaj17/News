package com.insightnews.feature.bookmark.data

import com.insightnews.core.database.dao.BookmarkDao
import com.insightnews.core.database.entity.BookmarkEntity
import com.insightnews.core.database.entity.toDomain
import com.insightnews.core.domain.model.Article
import com.insightnews.core.domain.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    private val bookmarkDao: BookmarkDao
) : BookmarkRepository {

    override fun getBookmarks(): Flow<List<Article>> {
        return bookmarkDao.getAllBookmarks().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun isBookmarked(articleId: String): Boolean {
        return bookmarkDao.isBookmarked(articleId)
    }

    override suspend fun addBookmark(article: Article) {
        bookmarkDao.insert(
            BookmarkEntity(
                articleId = article.id,
                title = article.title,
                description = article.description,
                sourceName = article.sourceName,
                url = article.url,
                imageUrl = article.imageUrl,
                publishedAt = article.publishedAt.time
            )
        )
    }

    override suspend fun removeBookmark(articleId: String) {
        bookmarkDao.getBookmark(articleId)?.let { bookmarkDao.delete(it) }
    }
}
