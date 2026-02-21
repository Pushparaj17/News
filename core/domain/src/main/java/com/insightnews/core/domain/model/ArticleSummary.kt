package com.insightnews.core.domain.model

/**
 * Domain model for AI-generated article summary.
 * Contains bullet points, keywords, and reading time estimate.
 */
data class ArticleSummary(
    val articleId: String,
    val summaryText: String,
    val bulletPoints: List<String>,
    val keywords: List<String>,
    val estimatedReadingTimeMinutes: Int,
    val topics: List<String>
) {
    val formattedReadingTime: String
        get() = when {
            estimatedReadingTimeMinutes < 1 -> "Under 1 min read"
            estimatedReadingTimeMinutes == 1 -> "1 min read"
            else -> "$estimatedReadingTimeMinutes min read"
        }
}
