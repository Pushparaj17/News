package com.insightnews.core.common.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DefaultDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IoDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainDispatcher

object CoroutineDispatchers {
    fun provideDefault(): CoroutineDispatcher = Dispatchers.Default
    fun provideIo(): CoroutineDispatcher = Dispatchers.IO
    fun provideMain(): CoroutineDispatcher = Dispatchers.Main
}
