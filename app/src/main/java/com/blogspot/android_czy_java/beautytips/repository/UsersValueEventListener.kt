package com.blogspot.android_czy_java.beautytips.repository

import com.blogspot.android_czy_java.beautytips.database.user.UserConverter
import com.blogspot.android_czy_java.beautytips.database.user.UserDao
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import timber.log.Timber

class UsersValueEventListener(private val usersToFetch: Set<String>,
                              private val userDao: UserDao) : ValueEventListener {

    override fun onDataChange(snapshot: DataSnapshot) {

        Thread(Runnable {

            for (userId in usersToFetch) {
                val userSnapshot = snapshot.child(userId)
                val user = UserConverter(userSnapshot).execute()
                user?.let { userDao.insertUser(it) }
            }

        }).start()

    }

    override fun onCancelled(error: DatabaseError) {
        Timber.e(error.message)
    }
}