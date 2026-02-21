package com.insightnews.feature.news.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.insightnews.core.database.dao.ArticleDao
import com.insightnews.core.database.entity.toEntity
import com.insightnews.core.domain.model.NewsCategory
import com.insightnews.core.network.api.GNewsApi
import com.insightnews.core.network.mapper.toDomainList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalPagingApi::class)
class NewsRemoteMediator(
    private val api: GNewsApi,
    private val articleDao: ArticleDao,
    private val apiKey: String,
    private val category: NewsCategory
) : RemoteMediator<Int, com.insightnews.core.database.entity.ArticleEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, com.insightnews.core.database.entity.ArticleEntity>
    ): MediatorResult = withContext(Dispatchers.IO) {
        try {
            when (loadType) {
                LoadType.REFRESH -> {
                    articleDao.deleteByCategory(category.apiValue)
                    val response = api.getTopHeadlines(
                        category = category.apiValue,
                        max = PAGE_SIZE,
                        apiKey = apiKey
                    )
                    val entities = response.articles.toDomainList()
                        .map { it.toEntity(category.apiValue) }
                    articleDao.insertAll(entities)
                }
                LoadType.PREPEND, LoadType.APPEND -> {
                    // GNews API doesn't support pagination - we have all 10 articles
                    return@withContext MediatorResult.Success(endOfPaginationReached = true)
                }
            }
            MediatorResult.Success(endOfPaginationReached = true)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    companion object {
        private const val PAGE_SIZE = 10
        private const val CACHE_MAX_AGE_MS = 6 * 60 * 60 * 1000L // 6 hours
    }
}
