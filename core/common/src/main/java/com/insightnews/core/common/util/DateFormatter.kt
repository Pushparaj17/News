package com.insightnews.core.common.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

object DateFormatter {

    private val relativeFormat = SimpleDateFormat("MMM d, yyyy • h:mm a", Locale.getDefault())
    private val shortFormat = SimpleDateFormat("MMM d", Locale.getDefault())

    fun formatRelative(date: Date): String {
        val now = System.currentTimeMillis()
        val diff = now - date.time
        return when {
            diff < TimeUnit.MINUTES.toMillis(1) -> "Just now"
            diff < TimeUnit.HOURS.toMillis(1) -> "${TimeUnit.MILLISECONDS.toMinutes(diff)}m ago"
            diff < TimeUnit.DAYS.toMillis(1) -> "${TimeUnit.MILLISECONDS.toHours(diff)}h ago"
            diff < TimeUnit.DAYS.toMillis(7) -> "${TimeUnit.MILLISECONDS.toDays(diff)}d ago"
            else -> shortFormat.format(date)
        }
    }

    fun formatFull(date: Date): String = relativeFormat.format(date)
}
