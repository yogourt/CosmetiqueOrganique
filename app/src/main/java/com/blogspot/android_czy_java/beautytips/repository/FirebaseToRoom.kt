package com.blogspot.android_czy_java.beautytips.repository

import com.blogspot.android_czy_java.beautytips.database.AppDatabase
import com.google.firebase.database.FirebaseDatabase
import io.reactivex.Single

public class FirebaseToRoom(private val database: AppDatabase)
{
    //this method will be called only once, when app is launched for the first time and there is no data

    fun observeFirebaseAndSaveToRoom(): Single<Boolean>
    {
        val firebaseDatabase = FirebaseDatabase.getInstance()

        return Single.create { emitter ->
            firebaseDatabase.getReference("tipList")
                    .addListenerForSingleValueEvent(TipListValueEventListener(database, emitter))
        }

    }
}