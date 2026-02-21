package com.insightnews.core.domain.model

/**
 * News categories supported by GNews API.
 */
enum class NewsCategory(val apiValue: String) {
    GENERAL("general"),
    WORLD("world"),
    NATION("nation"),
    BUSINESS("business"),
    TECHNOLOGY("technology"),
    ENTERTAINMENT("entertainment"),
    SPORTS("sports"),
    SCIENCE("science"),
    HEALTH("health")
}
