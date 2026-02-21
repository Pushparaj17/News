package com.insightnews.feature.summary.di

import com.insightnews.core.domain.repository.SummaryRepository
import com.insightnews.feature.summary.data.SummaryRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SummaryModule {

    @Binds
    @Singleton
    abstract fun bindSummaryRepository(impl: SummaryRepositoryImpl): SummaryRepository
}
