package com.insightnews.core.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.insightnews.core.domain.model.Article
import java.util.Date

@Entity(
    tableName = "bookmarks",
    indices = [Index(value = ["articleId"], unique = true)]
)
data class BookmarkEntity(
    @PrimaryKey val articleId: String,
    val title: String,
    val description: String?,
    val sourceName: String,
    val url: String,
    val imageUrl: String?,
    val publishedAt: Long,
    val savedAt: Long = System.currentTimeMillis()
)

fun BookmarkEntity.toDomain(): Article = Article(
    id = articleId,
    title = title,
    description = description,
    content = null,
    sourceName = sourceName,
    sourceUrl = null,
    author = null,
    url = url,
    imageUrl = imageUrl,
    publishedAt = Date(publishedAt),
    category = null,
    language = null,
    country = null
)
