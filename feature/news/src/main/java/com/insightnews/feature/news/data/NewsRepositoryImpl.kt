package com.insightnews.feature.news.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.insightnews.core.common.di.NewsApiKey
import com.insightnews.core.database.dao.ArticleDao
import com.insightnews.core.database.entity.toDomain
import com.insightnews.core.database.entity.toEntity
import com.insightnews.core.domain.model.Article
import com.insightnews.core.domain.model.NewsCategory
import com.insightnews.core.domain.repository.NewsRepository
import com.insightnews.core.network.api.GNewsApi
import com.insightnews.core.network.mapper.toDomainList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val api: GNewsApi,
    private val articleDao: ArticleDao,
    @NewsApiKey private val apiKey: String
) : NewsRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getTopHeadlines(category: NewsCategory): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = PAGE_SIZE
            ),
            remoteMediator = NewsRemoteMediator(
                api = api,
                articleDao = articleDao,
                apiKey = apiKey,
                category = category
            ),
            pagingSourceFactory = {
                articleDao.getArticlesByCategoryPagingSource(category.apiValue)
            }
        ).flow.map { pagingData -> pagingData.map { it.toDomain() } }
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun searchNews(query: String): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = PAGE_SIZE
            ),
            remoteMediator = SearchRemoteMediator(
                api = api,
                articleDao = articleDao,
                apiKey = apiKey,
                query = query
            ),
            pagingSourceFactory = {
                articleDao.getArticlesByCategoryPagingSource(SearchRemoteMediator.SEARCH_CATEGORY)
            }
        ).flow.map { pagingData -> pagingData.map { it.toDomain() } }
    }

    override suspend fun getArticleById(id: String): Article? {
        return articleDao.getArticleById(id)?.toDomain()
    }

    override suspend fun refreshHeadlines(category: NewsCategory): Result<List<Article>> {
        return try {
            val response = api.getTopHeadlines(
                category = category.apiValue,
                max = PAGE_SIZE,
                apiKey = apiKey
            )
            val articles = response.articles.toDomainList()
            val entities = articles.map { it.toEntity(category.apiValue) }
            articleDao.insertAll(entities)
            Result.success(articles)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    companion object {
        private const val PAGE_SIZE = 10
    }
}
