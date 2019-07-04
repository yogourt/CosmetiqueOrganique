package com.blogspot.android_czy_java.beautytips.database.repository

import android.content.Context
import com.blogspot.android_czy_java.beautytips.database.AppDatabase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

public class FirebaseToRoom(private val appContext: Context)
{

    /* TODO: finish
    init {
        if(thereIsNoData()) {
            observeFirebaseAndSaveToRoom()
        }
    }
    */

    //this method will be called only once, when app is launched for the first time and there is no data

    fun observeFirebaseAndSaveToRoom()
    {
        val firebaseDatabase = FirebaseDatabase.getInstance()

        firebaseDatabase.getReference("tipList")
                .addListenerForSingleValueEvent(TipListValueEventListener(appContext))

    }
}