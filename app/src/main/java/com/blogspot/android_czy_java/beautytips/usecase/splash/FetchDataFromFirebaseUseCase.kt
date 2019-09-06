package com.blogspot.android_czy_java.beautytips.usecase.splash

import com.blogspot.android_czy_java.beautytips.repository.FirebaseToRoom
import com.blogspot.android_czy_java.beautytips.usecase.common.NoRequestUseCaseInterface

class FetchDataFromFirebaseUseCase(private val firebaseToRoom: FirebaseToRoom) :
        NoRequestUseCaseInterface<Boolean> {

    override fun execute() = firebaseToRoom.observeFirebaseAndSaveToRoom()

}
