package com.blogspot.android_czy_java.beautytips.repository.forViewModels.user

import com.blogspot.android_czy_java.beautytips.database.error.ErrorModel
import com.blogspot.android_czy_java.beautytips.database.user.UserModel
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import io.reactivex.SingleEmitter

abstract class UserValueEventListener(private val emitter: SingleEmitter<UserModel>): ValueEventListener {
    override fun onCancelled(databaseError: DatabaseError) {
        emitter.onError(ErrorModel(databaseError.message, databaseError.code))
    }
}