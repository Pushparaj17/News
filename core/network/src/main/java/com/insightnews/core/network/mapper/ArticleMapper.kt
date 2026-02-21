package com.insightnews.core.network.mapper

import com.insightnews.core.domain.model.Article
import com.insightnews.core.network.model.GNewsArticle
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object ArticleMapper {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)

    fun GNewsArticle.toDomain(): Article {
        val publishedDate = try {
            dateFormat.parse(publishedAt) ?: Date()
        } catch (e: Exception) {
            Date()
        }
        val id = url.hashCode().toString().replace("-", "abs")
        return Article(
            id = id,
            title = title,
            description = description,
            content = content,
            sourceName = source.name,
            sourceUrl = source.url,
            author = author,
            url = url,
            imageUrl = image,
            publishedAt = publishedDate,
            category = null,
            language = null,
            country = null
        )
    }

    fun List<GNewsArticle>.toDomainList(): List<Article> = map { it.toDomain() }
}

// Top-level extensions for convenient usage
fun com.insightnews.core.network.model.GNewsArticle.toDomain(): Article =
    ArticleMapper.run { this@toDomain.toDomain() }

fun List<com.insightnews.core.network.model.GNewsArticle>.toDomainList(): List<Article> =
    ArticleMapper.run { this@toDomainList.toDomainList() }
