package com.insightnews.app.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.insightnews.core.domain.model.NewsCategory
import com.insightnews.core.domain.repository.NewsRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class NewsSyncWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val newsRepository: NewsRepository
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            listOf(
                NewsCategory.TECHNOLOGY,
                NewsCategory.BUSINESS,
                NewsCategory.SPORTS
            ).forEach { category ->
                newsRepository.refreshHeadlines(category)
            }
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
