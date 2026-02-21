package com.insightnews.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.insightnews.core.database.dao.ArticleDao
import com.insightnews.core.database.dao.BookmarkDao
import com.insightnews.core.database.entity.ArticleEntity
import com.insightnews.core.database.entity.BookmarkEntity

@Database(
    entities = [ArticleEntity::class, BookmarkEntity::class],
    version = 1,
    exportSchema = false
)
abstract class InsightNewsDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
    abstract fun bookmarkDao(): BookmarkDao
}
