package com.insightnews.core.network.model

import com.google.gson.annotations.SerializedName

data class GNewsResponse(
    @SerializedName("totalArticles") val totalArticles: Int,
    @SerializedName("articles") val articles: List<GNewsArticle>
)

data class GNewsArticle(
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String? = null,
    @SerializedName("content") val content: String? = null,
    @SerializedName("source") val source: GNewsSource,
    @SerializedName("author") val author: String? = null,
    @SerializedName("url") val url: String,
    @SerializedName("image") val image: String? = null,
    @SerializedName("publishedAt") val publishedAt: String
)

data class GNewsSource(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String? = null
)
