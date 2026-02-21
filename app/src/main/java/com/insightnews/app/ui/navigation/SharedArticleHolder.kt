package com.insightnews.app.ui.navigation

import com.insightnews.core.domain.model.Article

/**
 * Holds the selected article for navigation.
 * Used to pass Article between screens without Parcelable in domain layer.
 */
object SharedArticleHolder {
    var article: Article? = null
}
