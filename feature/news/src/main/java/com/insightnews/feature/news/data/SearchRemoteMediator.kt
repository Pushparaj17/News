package com.insightnews.feature.news.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.insightnews.core.database.dao.ArticleDao
import com.insightnews.core.database.entity.ArticleEntity
import com.insightnews.core.database.entity.toEntity
import com.insightnews.core.network.api.GNewsApi
import com.insightnews.core.network.mapper.toDomainList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalPagingApi::class)
class SearchRemoteMediator(
    private val api: GNewsApi,
    private val articleDao: ArticleDao,
    private val apiKey: String,
    private val query: String
) : RemoteMediator<Int, ArticleEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ArticleEntity>
    ): MediatorResult = withContext(Dispatchers.IO) {
        try {
            when (loadType) {
                LoadType.REFRESH -> {
                    articleDao.deleteByCategory(SEARCH_CATEGORY)
                    val response = api.search(
                        query = query,
                        max = PAGE_SIZE,
                        apiKey = apiKey
                    )
                    val entities = response.articles.toDomainList()
                        .map { it.toEntity(SEARCH_CATEGORY) }
                    articleDao.insertAll(entities)
                }
                LoadType.PREPEND, LoadType.APPEND -> {
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
        const val SEARCH_CATEGORY = "search"
    }
}
