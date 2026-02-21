package com.insightnews.core.network.di;

import com.insightnews.core.network.api.GNewsApi;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import retrofit2.Retrofit;

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
public final class NetworkModule_ProvideGNewsApiFactory implements Factory<GNewsApi> {
  private final Provider<Retrofit> retrofitProvider;

  public NetworkModule_ProvideGNewsApiFactory(Provider<Retrofit> retrofitProvider) {
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public GNewsApi get() {
    return provideGNewsApi(retrofitProvider.get());
  }

  public static NetworkModule_ProvideGNewsApiFactory create(Provider<Retrofit> retrofitProvider) {
    return new NetworkModule_ProvideGNewsApiFactory(retrofitProvider);
  }

  public static GNewsApi provideGNewsApi(Retrofit retrofit) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideGNewsApi(retrofit));
  }
}
