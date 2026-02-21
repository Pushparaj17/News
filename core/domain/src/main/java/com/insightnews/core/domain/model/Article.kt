package com.insightnews.core.domain.model

import java.util.Date

/**
 * Domain model for a news article.
 * No Android framework references in Domain layer.
 */
data class Article(
    val id: String,
    val title: String,
    val description: String?,
    val content: String?,
    val sourceName: String,
    val sourceUrl: String?,
    val author: String?,
    val url: String,
    val imageUrl: String?,
    val publishedAt: Date,
    val category: String?,
    val language: String?,
    val country: String?
) {
    val sourceBias: SourceBias
        get() = SourceBias.fromSource(sourceName)
}

/**
 * Bias indicator based on known media source characteristics.
 * Used for transparency in news consumption.
 */
enum class SourceBias {
    UNKNOWN,
    LEFT_LEANING,
    RIGHT_LEANING,
    CENTER,
    INTERNATIONAL;

    companion object {
        private val LEFT_SOURCES = setOf(
            "the guardian", "bbc", "npr", "reuters", "associated press",
            "cnn", "msnbc", "huffpost", "vox", "politico"
        )
        private val RIGHT_SOURCES = setOf(
            "fox news", "breitbart", "daily wire", "the blaze"
        )
        private val CENTER_SOURCES = setOf(
            "reuters", "associated press", "bloomberg", "wsj"
        )

        fun fromSource(sourceName: String): SourceBias {
            val normalized = sourceName.lowercase().trim()
            return when {
                CENTER_SOURCES.any { normalized.contains(it) } -> CENTER
                LEFT_SOURCES.any { normalized.contains(it) } -> LEFT_LEANING
                RIGHT_SOURCES.any { normalized.contains(it) } -> RIGHT_LEANING
                normalized.contains("al jazeera") || normalized.contains("dw") -> INTERNATIONAL
                else -> UNKNOWN
            }
        }
    }
}
