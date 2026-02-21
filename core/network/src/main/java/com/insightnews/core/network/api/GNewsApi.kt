package com.insightnews.core.network.api

import com.insightnews.core.network.model.GNewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * GNews API interface.
 * Base URL: https://gnews.io/api/v4/
 * Free tier: 100 requests/day, 10 articles per request
 */
interface GNewsApi {

    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("category") category: String,
        @Query("lang") lang: String = "en",
        @Query("country") country: String = "us",
        @Query("max") max: Int = 10,
        @Query("apikey") apiKey: String
    ): GNewsResponse

    @GET("search")
    suspend fun search(
        @Query("q") query: String,
        @Query("lang") lang: String = "en",
        @Query("country") country: String = "us",
        @Query("max") max: Int = 10,
        @Query("apikey") apiKey: String
    ): GNewsResponse
}
