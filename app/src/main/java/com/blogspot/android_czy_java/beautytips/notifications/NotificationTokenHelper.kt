package com.blogspot.android_czy_java.beautytips.notifications

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.iid.FirebaseInstanceId


object NotificationTokenHelper {

    fun saveUserNotificationToken() {

        //pass user notification token to the server
        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener { task ->
            val token = task.result?.token ?: return@addOnCompleteListener

            FirebaseAuth.getInstance().currentUser?.uid?.let {
                FirebaseDatabase.getInstance()
                        .getReference("users/$it/token")
                        .setValue(token)
            }
        }
    }
}
