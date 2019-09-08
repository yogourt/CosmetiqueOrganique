package com.blogspot.android_czy_java.beautytips.repository.forViewModels.user

import com.blogspot.android_czy_java.beautytips.database.user.UserDao
import com.blogspot.android_czy_java.beautytips.database.user.UserModel
import com.google.firebase.database.FirebaseDatabase
import io.reactivex.SingleEmitter

class UserRepository(private val userDao: UserDao) {

    fun getUserByFirebaseId(firebaseId: String) = userDao.getUserByFirebaseId(firebaseId)

    fun insertUser(user: UserModel) = userDao.insertUser(user)

    fun insertCurrentFirebaseUser(id: String, emitter: SingleEmitter<UserModel>) {

            FirebaseDatabase.getInstance().getReference("userNicknames/$id")
                    .addListenerForSingleValueEvent(
                            NicknameValueEventListener(
                                    emitter,
                                    id,
                                    this
                            ))
    }
}