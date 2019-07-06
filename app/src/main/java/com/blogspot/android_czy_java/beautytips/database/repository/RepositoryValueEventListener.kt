package com.blogspot.android_czy_java.beautytips.database.repository

import com.blogspot.android_czy_java.beautytips.database.error.ErrorModel
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import io.reactivex.SingleEmitter

abstract class RepositoryValueEventListener(private val emitter: SingleEmitter<Boolean>): ValueEventListener {

    override fun onCancelled(databaseError: DatabaseError) {
        emitter.onError(ErrorModel(databaseError.message, databaseError.code))
    }
}