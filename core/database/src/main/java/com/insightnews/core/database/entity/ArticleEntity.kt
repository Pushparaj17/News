package com.insightnews.core.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.insightnews.core.domain.model.Article
import java.util.Date

@Entity(
    tableName = "articles",
    indices = [Index(value = ["url"], unique = true)]
)
data class ArticleEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String?,
    val content: String?,
    val sourceName: String,
    val sourceUrl: String?,
    val author: String?,
    val url: String,
    val imageUrl: String?,
    val publishedAt: Long,
    val category: String?,
    val cachedAt: Long = System.currentTimeMillis()
)

fun ArticleEntity.toDomain(): Article = Article(
    id = id,
    title = title,
    description = description,
    content = content,
    sourceName = sourceName,
    sourceUrl = sourceUrl,
    author = author,
    url = url,
    imageUrl = imageUrl,
    publishedAt = Date(publishedAt),
    category = category,
    language = null,
    country = null
)

fun Article.toEntity(categoryOverride: String? = null): ArticleEntity = ArticleEntity(
    id = id,
    title = title,
    description = description,
    content = content,
    sourceName = sourceName,
    sourceUrl = sourceUrl,
    author = author,
    url = url,
    imageUrl = imageUrl,
    publishedAt = publishedAt.time,
    category = categoryOverride ?: category
)
