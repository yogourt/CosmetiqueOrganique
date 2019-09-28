package com.blogspot.android_czy_java.beautytips.di.repository.comment

import com.blogspot.android_czy_java.beautytips.database.detail.DetailDao
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.comment.CommentRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CommentRepositoryModule {

    @Provides
    @Singleton
    fun provideCommentRepository(detailDao: DetailDao) = CommentRepository(detailDao)
}