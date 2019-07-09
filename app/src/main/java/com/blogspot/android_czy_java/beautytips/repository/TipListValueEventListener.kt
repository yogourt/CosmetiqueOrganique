package com.blogspot.android_czy_java.beautytips.repository

import com.blogspot.android_czy_java.beautytips.database.AppDatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import io.reactivex.SingleEmitter

class TipListValueEventListener(private val database: AppDatabase, private val emitter: SingleEmitter<Boolean>) :
        RepositoryValueEventListener(emitter) {

    override fun onDataChange(dataSnapshot: DataSnapshot) {

        FirebaseDatabase.getInstance().getReference("tips")
                .addListenerForSingleValueEvent(TipsValueEventListener(database, dataSnapshot, emitter))
    }
}