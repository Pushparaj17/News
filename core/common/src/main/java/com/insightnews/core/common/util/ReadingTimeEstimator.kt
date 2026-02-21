package com.insightnews.core.common.util

private const val WORDS_PER_MINUTE = 200

/**
 * Estimates reading time in minutes based on word count.
 * Average adult reading speed: ~200 words per minute.
 */
object ReadingTimeEstimator {

    fun estimateMinutes(text: String?): Int {
        if (text.isNullOrBlank()) return 0
        val words = text.split(Regex("\\s+")).filter { it.isNotEmpty() }.size
        return maxOf(1, (words / WORDS_PER_MINUTE))
    }

    fun formatReadingTime(minutes: Int): String = when {
        minutes < 1 -> "Under 1 min read"
        minutes == 1 -> "1 min read"
        else -> "$minutes min read"
    }
}
