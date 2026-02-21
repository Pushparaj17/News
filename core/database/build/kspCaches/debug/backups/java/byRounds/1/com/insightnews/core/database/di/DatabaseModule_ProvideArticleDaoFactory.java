package com.insightnews.core.database.di;

import com.insightnews.core.database.InsightNewsDatabase;
import com.insightnews.core.database.dao.ArticleDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class DatabaseModule_ProvideArticleDaoFactory implements Factory<ArticleDao> {
  private final Provider<InsightNewsDatabase> databaseProvider;

  public DatabaseModule_ProvideArticleDaoFactory(Provider<InsightNewsDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public ArticleDao get() {
    return provideArticleDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideArticleDaoFactory create(
      Provider<InsightNewsDatabase> databaseProvider) {
    return new DatabaseModule_ProvideArticleDaoFactory(databaseProvider);
  }

  public static ArticleDao provideArticleDao(InsightNewsDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideArticleDao(database));
  }
}
