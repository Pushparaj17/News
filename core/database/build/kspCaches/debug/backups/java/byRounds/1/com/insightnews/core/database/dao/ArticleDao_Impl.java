package com.insightnews.core.database.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagingSource;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.paging.LimitOffsetPagingSource;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.insightnews.core.database.entity.ArticleEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class ArticleDao_Impl implements ArticleDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ArticleEntity> __insertionAdapterOfArticleEntity;

  private final SharedSQLiteStatement __preparedStmtOfClearAll;

  private final SharedSQLiteStatement __preparedStmtOfDeleteOlderThan;

  private final SharedSQLiteStatement __preparedStmtOfDeleteByCategory;

  public ArticleDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfArticleEntity = new EntityInsertionAdapter<ArticleEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `articles` (`id`,`title`,`description`,`content`,`sourceName`,`sourceUrl`,`author`,`url`,`imageUrl`,`publishedAt`,`category`,`cachedAt`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ArticleEntity entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getTitle());
        if (entity.getDescription() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getDescription());
        }
        if (entity.getContent() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getContent());
        }
        statement.bindString(5, entity.getSourceName());
        if (entity.getSourceUrl() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getSourceUrl());
        }
        if (entity.getAuthor() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getAuthor());
        }
        statement.bindString(8, entity.getUrl());
        if (entity.getImageUrl() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getImageUrl());
        }
        statement.bindLong(10, entity.getPublishedAt());
        if (entity.getCategory() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getCategory());
        }
        statement.bindLong(12, entity.getCachedAt());
      }
    };
    this.__preparedStmtOfClearAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM articles";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteOlderThan = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM articles WHERE cachedAt < ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteByCategory = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM articles WHERE category = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertAll(final List<ArticleEntity> articles,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfArticleEntity.insert(articles);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object clearAll(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfClearAll.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfClearAll.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteOlderThan(final long maxAge, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteOlderThan.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, maxAge);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteOlderThan.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteByCategory(final String category,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteByCategory.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, category);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteByCategory.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public PagingSource<Integer, ArticleEntity> getArticlesPagingSource() {
    final String _sql = "SELECT * FROM articles ORDER BY publishedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new LimitOffsetPagingSource<ArticleEntity>(_statement, __db, "articles") {
      @Override
      @NonNull
      protected List<ArticleEntity> convertRows(@NonNull final Cursor cursor) {
        final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(cursor, "id");
        final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(cursor, "title");
        final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(cursor, "description");
        final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(cursor, "content");
        final int _cursorIndexOfSourceName = CursorUtil.getColumnIndexOrThrow(cursor, "sourceName");
        final int _cursorIndexOfSourceUrl = CursorUtil.getColumnIndexOrThrow(cursor, "sourceUrl");
        final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(cursor, "author");
        final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(cursor, "url");
        final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(cursor, "imageUrl");
        final int _cursorIndexOfPublishedAt = CursorUtil.getColumnIndexOrThrow(cursor, "publishedAt");
        final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(cursor, "category");
        final int _cursorIndexOfCachedAt = CursorUtil.getColumnIndexOrThrow(cursor, "cachedAt");
        final List<ArticleEntity> _result = new ArrayList<ArticleEntity>(cursor.getCount());
        while (cursor.moveToNext()) {
          final ArticleEntity _item;
          final String _tmpId;
          _tmpId = cursor.getString(_cursorIndexOfId);
          final String _tmpTitle;
          _tmpTitle = cursor.getString(_cursorIndexOfTitle);
          final String _tmpDescription;
          if (cursor.isNull(_cursorIndexOfDescription)) {
            _tmpDescription = null;
          } else {
            _tmpDescription = cursor.getString(_cursorIndexOfDescription);
          }
          final String _tmpContent;
          if (cursor.isNull(_cursorIndexOfContent)) {
            _tmpContent = null;
          } else {
            _tmpContent = cursor.getString(_cursorIndexOfContent);
          }
          final String _tmpSourceName;
          _tmpSourceName = cursor.getString(_cursorIndexOfSourceName);
          final String _tmpSourceUrl;
          if (cursor.isNull(_cursorIndexOfSourceUrl)) {
            _tmpSourceUrl = null;
          } else {
            _tmpSourceUrl = cursor.getString(_cursorIndexOfSourceUrl);
          }
          final String _tmpAuthor;
          if (cursor.isNull(_cursorIndexOfAuthor)) {
            _tmpAuthor = null;
          } else {
            _tmpAuthor = cursor.getString(_cursorIndexOfAuthor);
          }
          final String _tmpUrl;
          _tmpUrl = cursor.getString(_cursorIndexOfUrl);
          final String _tmpImageUrl;
          if (cursor.isNull(_cursorIndexOfImageUrl)) {
            _tmpImageUrl = null;
          } else {
            _tmpImageUrl = cursor.getString(_cursorIndexOfImageUrl);
          }
          final long _tmpPublishedAt;
          _tmpPublishedAt = cursor.getLong(_cursorIndexOfPublishedAt);
          final String _tmpCategory;
          if (cursor.isNull(_cursorIndexOfCategory)) {
            _tmpCategory = null;
          } else {
            _tmpCategory = cursor.getString(_cursorIndexOfCategory);
          }
          final long _tmpCachedAt;
          _tmpCachedAt = cursor.getLong(_cursorIndexOfCachedAt);
          _item = new ArticleEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpContent,_tmpSourceName,_tmpSourceUrl,_tmpAuthor,_tmpUrl,_tmpImageUrl,_tmpPublishedAt,_tmpCategory,_tmpCachedAt);
          _result.add(_item);
        }
        return _result;
      }
    };
  }

  @Override
  public PagingSource<Integer, ArticleEntity> getArticlesByCategoryPagingSource(
      final String category) {
    final String _sql = "SELECT * FROM articles WHERE category = ? ORDER BY publishedAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, category);
    return new LimitOffsetPagingSource<ArticleEntity>(_statement, __db, "articles") {
      @Override
      @NonNull
      protected List<ArticleEntity> convertRows(@NonNull final Cursor cursor) {
        final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(cursor, "id");
        final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(cursor, "title");
        final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(cursor, "description");
        final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(cursor, "content");
        final int _cursorIndexOfSourceName = CursorUtil.getColumnIndexOrThrow(cursor, "sourceName");
        final int _cursorIndexOfSourceUrl = CursorUtil.getColumnIndexOrThrow(cursor, "sourceUrl");
        final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(cursor, "author");
        final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(cursor, "url");
        final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(cursor, "imageUrl");
        final int _cursorIndexOfPublishedAt = CursorUtil.getColumnIndexOrThrow(cursor, "publishedAt");
        final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(cursor, "category");
        final int _cursorIndexOfCachedAt = CursorUtil.getColumnIndexOrThrow(cursor, "cachedAt");
        final List<ArticleEntity> _result = new ArrayList<ArticleEntity>(cursor.getCount());
        while (cursor.moveToNext()) {
          final ArticleEntity _item;
          final String _tmpId;
          _tmpId = cursor.getString(_cursorIndexOfId);
          final String _tmpTitle;
          _tmpTitle = cursor.getString(_cursorIndexOfTitle);
          final String _tmpDescription;
          if (cursor.isNull(_cursorIndexOfDescription)) {
            _tmpDescription = null;
          } else {
            _tmpDescription = cursor.getString(_cursorIndexOfDescription);
          }
          final String _tmpContent;
          if (cursor.isNull(_cursorIndexOfContent)) {
            _tmpContent = null;
          } else {
            _tmpContent = cursor.getString(_cursorIndexOfContent);
          }
          final String _tmpSourceName;
          _tmpSourceName = cursor.getString(_cursorIndexOfSourceName);
          final String _tmpSourceUrl;
          if (cursor.isNull(_cursorIndexOfSourceUrl)) {
            _tmpSourceUrl = null;
          } else {
            _tmpSourceUrl = cursor.getString(_cursorIndexOfSourceUrl);
          }
          final String _tmpAuthor;
          if (cursor.isNull(_cursorIndexOfAuthor)) {
            _tmpAuthor = null;
          } else {
            _tmpAuthor = cursor.getString(_cursorIndexOfAuthor);
          }
          final String _tmpUrl;
          _tmpUrl = cursor.getString(_cursorIndexOfUrl);
          final String _tmpImageUrl;
          if (cursor.isNull(_cursorIndexOfImageUrl)) {
            _tmpImageUrl = null;
          } else {
            _tmpImageUrl = cursor.getString(_cursorIndexOfImageUrl);
          }
          final long _tmpPublishedAt;
          _tmpPublishedAt = cursor.getLong(_cursorIndexOfPublishedAt);
          final String _tmpCategory;
          if (cursor.isNull(_cursorIndexOfCategory)) {
            _tmpCategory = null;
          } else {
            _tmpCategory = cursor.getString(_cursorIndexOfCategory);
          }
          final long _tmpCachedAt;
          _tmpCachedAt = cursor.getLong(_cursorIndexOfCachedAt);
          _item = new ArticleEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpContent,_tmpSourceName,_tmpSourceUrl,_tmpAuthor,_tmpUrl,_tmpImageUrl,_tmpPublishedAt,_tmpCategory,_tmpCachedAt);
          _result.add(_item);
        }
        return _result;
      }
    };
  }

  @Override
  public Object getArticleById(final String id,
      final Continuation<? super ArticleEntity> $completion) {
    final String _sql = "SELECT * FROM articles WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<ArticleEntity>() {
      @Override
      @Nullable
      public ArticleEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfSourceName = CursorUtil.getColumnIndexOrThrow(_cursor, "sourceName");
          final int _cursorIndexOfSourceUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "sourceUrl");
          final int _cursorIndexOfAuthor = CursorUtil.getColumnIndexOrThrow(_cursor, "author");
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
          final int _cursorIndexOfPublishedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "publishedAt");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfCachedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "cachedAt");
          final ArticleEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpContent;
            if (_cursor.isNull(_cursorIndexOfContent)) {
              _tmpContent = null;
            } else {
              _tmpContent = _cursor.getString(_cursorIndexOfContent);
            }
            final String _tmpSourceName;
            _tmpSourceName = _cursor.getString(_cursorIndexOfSourceName);
            final String _tmpSourceUrl;
            if (_cursor.isNull(_cursorIndexOfSourceUrl)) {
              _tmpSourceUrl = null;
            } else {
              _tmpSourceUrl = _cursor.getString(_cursorIndexOfSourceUrl);
            }
            final String _tmpAuthor;
            if (_cursor.isNull(_cursorIndexOfAuthor)) {
              _tmpAuthor = null;
            } else {
              _tmpAuthor = _cursor.getString(_cursorIndexOfAuthor);
            }
            final String _tmpUrl;
            _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            final String _tmpImageUrl;
            if (_cursor.isNull(_cursorIndexOfImageUrl)) {
              _tmpImageUrl = null;
            } else {
              _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
            }
            final long _tmpPublishedAt;
            _tmpPublishedAt = _cursor.getLong(_cursorIndexOfPublishedAt);
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            final long _tmpCachedAt;
            _tmpCachedAt = _cursor.getLong(_cursorIndexOfCachedAt);
            _result = new ArticleEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpContent,_tmpSourceName,_tmpSourceUrl,_tmpAuthor,_tmpUrl,_tmpImageUrl,_tmpPublishedAt,_tmpCategory,_tmpCachedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
