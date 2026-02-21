package com.insightnews.core.database.di

import android.content.Context
import androidx.room.Room
import com.insightnews.core.database.InsightNewsDatabase
import com.insightnews.core.database.dao.ArticleDao
import com.insightnews.core.database.dao.BookmarkDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): InsightNewsDatabase = Room.databaseBuilder(
        context,
        InsightNewsDatabase::class.java,
        "insight_news_db"
    ).build()

    @Provides
    @Singleton
    fun provideArticleDao(database: InsightNewsDatabase): ArticleDao =
        database.articleDao()

    @Provides
    @Singleton
    fun provideBookmarkDao(database: InsightNewsDatabase): BookmarkDao =
        database.bookmarkDao()
}
