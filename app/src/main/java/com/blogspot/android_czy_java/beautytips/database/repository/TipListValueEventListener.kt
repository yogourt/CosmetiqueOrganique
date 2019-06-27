package com.blogspot.android_czy_java.beautytips.database.repository

import android.content.Context
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase

class TipListValueEventListener(private val appContext: Context) : RepositoryValueEventListener(appContext) {

    override fun onDataChange(dataSnapshot: DataSnapshot) {

        FirebaseDatabase.getInstance().getReference("tips")
                .addListenerForSingleValueEvent(TipsValueEventListener(appContext, dataSnapshot))
    }
}