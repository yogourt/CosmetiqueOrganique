package com.blogspot.android_czy_java.beautytips.database.repository

import android.content.Context
import com.blogspot.android_czy_java.beautytips.database.AppDatabase
import com.blogspot.android_czy_java.beautytips.database.error.ErrorModel
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

abstract class RepositoryValueEventListener(private val appContext: Context): ValueEventListener {

    override fun onCancelled(databaseError: DatabaseError) {
        AppDatabase.getInstance(appContext).errorDao().insertError(ErrorModel(
                databaseError.message, databaseError.code))
    }
}