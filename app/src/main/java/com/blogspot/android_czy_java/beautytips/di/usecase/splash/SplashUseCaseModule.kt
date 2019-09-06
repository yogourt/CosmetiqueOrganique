package com.blogspot.android_czy_java.beautytips.di.usecase.splash

import com.blogspot.android_czy_java.beautytips.repository.FirebaseToRoom
import com.blogspot.android_czy_java.beautytips.usecase.splash.FetchDataFromFirebaseUseCase
import dagger.Module
import dagger.Provides

@Module
class SplashUseCaseModule {

    @Provides
    fun provideFetchDataFromFirebaseUseCase(firebaseToRoom: FirebaseToRoom) =
            FetchDataFromFirebaseUseCase(firebaseToRoom)
}