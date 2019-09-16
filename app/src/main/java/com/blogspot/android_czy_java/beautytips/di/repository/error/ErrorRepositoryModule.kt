package com.blogspot.android_czy_java.beautytips.di.repository.error

import com.blogspot.android_czy_java.beautytips.database.error.ErrorDao
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.error.ErrorRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ErrorRepositoryModule {

    @Provides
    @Singleton
    fun provideErrorRepository(errorDao: ErrorDao) = ErrorRepository(errorDao)
}