package com.blogspot.android_czy_java.beautytips.usecase.account.login

import com.blogspot.android_czy_java.beautytips.database.FirebaseKeys
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.iid.FirebaseInstanceId

class PassTokenToFirebaseUseCase {

    fun execute(userId: String) {
        FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        return@OnCompleteListener
                    }

                    // Get new Instance ID token
                    val token = task.result?.token

                    token?.let {
                        FirebaseDatabase.getInstance()
                                .getReference(
                                        FirebaseKeys.REFERENCE_USERS +
                                                "/$userId/${FirebaseKeys.REFERENCE_TOKEN}")
                                .setValue(token)
                    }

                })

    }
}