package com.blogspot.android_czy_java.beautytips.repository

import com.blogspot.android_czy_java.beautytips.database.AppDatabase
import com.blogspot.android_czy_java.beautytips.database.FirebaseKeys
import com.google.firebase.database.FirebaseDatabase
import io.reactivex.Single

class FirebaseToRoom(private val database: AppDatabase)
{
    fun observeFirebaseAndSaveToRoom(): Single<Boolean>
    {
        val firebaseDatabase = FirebaseDatabase.getInstance()

        return Single.create { emitter ->
            firebaseDatabase.getReference("tipList")
                    .addListenerForSingleValueEvent(TipListValueEventListener(database, emitter))

            firebaseDatabase.getReference(FirebaseKeys.REFERENCE_COMMENTS)
                    .addValueEventListener(CommentsValueEventListener(database))
        }
    }
}