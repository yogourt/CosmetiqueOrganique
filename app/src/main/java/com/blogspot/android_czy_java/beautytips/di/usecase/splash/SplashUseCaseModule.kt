package com.blogspot.android_czy_java.beautytips.di.usecase.splash

import android.content.SharedPreferences
import com.blogspot.android_czy_java.beautytips.repository.FirebaseToRoom
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.comment.CommentRepository
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.error.ErrorRepository
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.user.UserRepository
import com.blogspot.android_czy_java.beautytips.usecase.account.UpdateUserDataInFirebaseUseCase
import com.blogspot.android_czy_java.beautytips.usecase.account.userlist.PushUserListToFirebaseUseCase
import com.blogspot.android_czy_java.beautytips.usecase.comment.PushCommentToFirebaseUseCase
import com.blogspot.android_czy_java.beautytips.usecase.splash.FetchDataFromFirebaseUseCase
import com.blogspot.android_czy_java.beautytips.usecase.splash.PushLocalDataUseCase
import com.blogspot.android_czy_java.beautytips.usecase.splash.UpdateInBackgroundUseCase
import dagger.Module
import dagger.Provides

@Module
class SplashUseCaseModule {

    @Provides
    fun provideFetchDataFromFirebaseUseCase(firebaseToRoom: FirebaseToRoom) =
            FetchDataFromFirebaseUseCase(firebaseToRoom)

    @Provides
    fun provideUpdateInBackgroundUseCase(fetchDataFromFirebaseUseCase: FetchDataFromFirebaseUseCase,
                                         pushLocalDataUseCase: PushLocalDataUseCase,
                                         prefs: SharedPreferences) =
            UpdateInBackgroundUseCase(fetchDataFromFirebaseUseCase,
                    pushLocalDataUseCase,
                    prefs)

    @Provides
    fun providePushLocalDataUseCase(commentRepository: CommentRepository,
                                    pushCommentToFirebaseUseCase: PushCommentToFirebaseUseCase,
                                    errorRepository: ErrorRepository,
                                    pushUserListToFirebaseUseCase: PushUserListToFirebaseUseCase,
                                    updateUserDataInFirebaseUseCase: UpdateUserDataInFirebaseUseCase,
                                    userRepository: UserRepository) =
            PushLocalDataUseCase(commentRepository,
                    pushCommentToFirebaseUseCase,
                    errorRepository,
                    updateUserDataInFirebaseUseCase,
                    userRepository,
                    pushUserListToFirebaseUseCase)
}