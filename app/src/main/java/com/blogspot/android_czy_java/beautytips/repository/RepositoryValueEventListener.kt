package com.blogspot.android_czy_java.beautytips.repository

import com.blogspot.android_czy_java.beautytips.database.error.ErrorModel
import com.blogspot.android_czy_java.beautytips.exception.common.FirebaseValueEventListenerException
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import io.reactivex.SingleEmitter

abstract class RepositoryValueEventListener(private val emitter: SingleEmitter<Boolean>): ValueEventListener {

    override fun onCancelled(databaseError: DatabaseError) {
        emitter.onError(FirebaseValueEventListenerException(databaseError.message))
    }
}