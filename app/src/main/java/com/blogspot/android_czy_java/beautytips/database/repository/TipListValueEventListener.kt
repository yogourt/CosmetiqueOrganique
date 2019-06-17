package com.blogspot.android_czy_java.beautytips.database.repository

import com.blogspot.android_czy_java.beautytips.listView.model.TipListItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class TipListValueEventListener : ValueEventListener {

    override fun onDataChange(dataSnapshot: DataSnapshot) {

        Runnable {
            for (item in dataSnapshot.children) {
                val tip = item.getValue(TipListItem::class.java)

            }
        }

    }

    override fun onCancelled(databaseError: DatabaseError) {

    }
}