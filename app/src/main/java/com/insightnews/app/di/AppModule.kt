package com.insightnews.app.di

import com.insightnews.app.BuildConfig
import com.insightnews.core.common.di.NewsApiKey
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    @NewsApiKey
    fun provideNewsApiKey(): String = BuildConfig.NEWS_API_KEY
}
