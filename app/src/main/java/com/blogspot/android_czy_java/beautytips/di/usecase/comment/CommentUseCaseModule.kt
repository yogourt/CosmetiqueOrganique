package com.blogspot.android_czy_java.beautytips.di.usecase.comment

import com.blogspot.android_czy_java.beautytips.di.repository.comment.CommentRepositoryModule
import com.blogspot.android_czy_java.beautytips.di.usecase.account.AccountUseCaseModule
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.comment.CommentRepository
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.detail.RecipeDetailRepository
import com.blogspot.android_czy_java.beautytips.usecase.account.GetCurrentUserUseCase
import com.blogspot.android_czy_java.beautytips.usecase.comment.AddCommentUseCase
import com.blogspot.android_czy_java.beautytips.usecase.comment.GetCommentNumberUseCase
import com.blogspot.android_czy_java.beautytips.usecase.comment.LoadCommentsUseCase
import com.blogspot.android_czy_java.beautytips.usecase.comment.PushCommentToFirebaseUseCase
import dagger.Module
import dagger.Provides

@Module(includes = [
    CommentRepositoryModule::class,
    AccountUseCaseModule::class
])
class CommentUseCaseModule {

    @Provides
    fun provideLoadCommentsUseCase(repository: CommentRepository) = LoadCommentsUseCase(repository)

    @Provides
    fun provideGetCommentNumberUseCase(repository: CommentRepository) =
            GetCommentNumberUseCase(repository)

    @Provides
    fun provideAddCommentUseCase(getCurrentUserUseCase: GetCurrentUserUseCase,
                                 pushCommentToFirebaseUseCase: PushCommentToFirebaseUseCase,
                                 commentRepository: CommentRepository) =
            AddCommentUseCase(getCurrentUserUseCase,
                    commentRepository,
                    pushCommentToFirebaseUseCase)

    @Provides
    fun providePushCommentToFirebaseUseCase(commentRepository: CommentRepository) =
            PushCommentToFirebaseUseCase(commentRepository)


}