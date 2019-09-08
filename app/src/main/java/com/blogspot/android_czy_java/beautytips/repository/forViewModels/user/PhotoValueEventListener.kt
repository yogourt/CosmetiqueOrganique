package com.blogspot.android_czy_java.beautytips.repository.forViewModels.user

import android.os.Handler
import com.blogspot.android_czy_java.beautytips.database.user.UserModel
import com.google.firebase.database.DataSnapshot
import io.reactivex.SingleEmitter

class PhotoValueEventListener(private val emitter: SingleEmitter<UserModel>,
                              private val nickname: String,
                              private val firebaseId: String,
                              private val userRepository: UserRepository) :
        UserValueEventListener(emitter) {

    override fun onDataChange(data: DataSnapshot) {

        Thread(Runnable {
            val photoUrl: String =
                    if (data.value == null) ""
                    else data.value.toString()

            val user = UserModel(firebaseId, nickname, photoUrl)

            userRepository.insertUser(user)

            emitter.onSuccess(user)
        }).start()

    }
}