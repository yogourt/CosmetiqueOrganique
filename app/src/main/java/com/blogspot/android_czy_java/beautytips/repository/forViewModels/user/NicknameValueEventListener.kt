package com.blogspot.android_czy_java.beautytips.repository.forViewModels.user

import com.blogspot.android_czy_java.beautytips.database.user.UserModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import io.reactivex.SingleEmitter

class NicknameValueEventListener(private val emitter: SingleEmitter<UserModel>,
                                 private val firebaseId: String,
                                 private val userRepository: UserRepository) : UserValueEventListener(emitter) {

    override fun onDataChange(data: DataSnapshot) {

        val nickname: String =
                if (data.value == null) ""
                else data.value.toString()

        FirebaseDatabase
                .getInstance()
                .getReference("userPhotos/$firebaseId")
                .addListenerForSingleValueEvent(
                        PhotoValueEventListener(
                                emitter,
                                nickname,
                                firebaseId,
                                userRepository
                        ))


    }
}