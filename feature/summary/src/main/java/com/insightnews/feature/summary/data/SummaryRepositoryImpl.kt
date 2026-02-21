package com.insightnews.feature.summary.data

import com.insightnews.core.common.util.ReadingTimeEstimator
import com.insightnews.core.domain.model.Article
import com.insightnews.core.domain.model.ArticleSummary
import com.insightnews.core.domain.repository.SummaryRepository
import javax.inject.Inject

/**
 * Local summarization using extractive NLP techniques.
 * No external API required - works offline.
 * Can be swapped for AI API implementation via the abstraction layer.
 */
class SummaryRepositoryImpl @Inject constructor() : SummaryRepository {

    override suspend fun summarizeArticle(article: Article): Result<ArticleSummary> {
        return try {
            val fullText = buildString {
                article.title.let { if (it.isNotBlank()) append("$it. ") }
                article.description?.let { if (it.isNotBlank()) append("$it ") }
                article.content?.let { if (it.isNotBlank()) append(it) }
            }
            val bulletPoints = extractBulletPoints(fullText)
            val keywords = extractKeywords(fullText)
            val readingTime = ReadingTimeEstimator.estimateMinutes(fullText)
            val topics = extractTopics(fullText, article.title)
            val summaryText = bulletPoints.take(3).joinToString(" ") { it.trim().trimEnd('.') }

            Result.success(
                ArticleSummary(
                    articleId = article.id,
                    summaryText = summaryText.ifBlank { article.description ?: article.title },
                    bulletPoints = bulletPoints.take(5),
                    keywords = keywords.take(10),
                    estimatedReadingTimeMinutes = maxOf(1, readingTime),
                    topics = topics.take(5)
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun extractBulletPoints(text: String): List<String> {
        val sentences = text.split(Regex("[.!?]+"))
            .map { it.trim() }
            .filter { it.length > 30 }
            .distinct()
        return sentences.take(5).map { it + "." }
    }

    private fun extractKeywords(text: String): List<String> {
        val stopWords = setOf(
            "the", "a", "an", "and", "or", "but", "in", "on", "at", "to", "for",
            "of", "with", "by", "from", "is", "are", "was", "were", "be", "been",
            "being", "have", "has", "had", "do", "does", "did", "will", "would",
            "could", "should", "may", "might", "must", "can", "this", "that"
        )
        val words = text.lowercase()
            .split(Regex("\\W+"))
            .filter { it.length > 3 && !stopWords.contains(it) }
        val wordFreq = words.groupingBy { it }.eachCount()
        return wordFreq.entries
            .sortedByDescending { it.value }
            .map { it.key.replaceFirstChar { c -> c.uppercase() } }
    }

    private fun extractTopics(text: String, title: String): List<String> {
        val combined = "$title $text"
        val keywords = extractKeywords(combined)
        return keywords.take(5)
    }
}
