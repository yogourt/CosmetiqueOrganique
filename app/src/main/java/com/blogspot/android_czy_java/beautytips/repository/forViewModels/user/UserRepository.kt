package com.blogspot.android_czy_java.beautytips.repository.forViewModels.user

import com.blogspot.android_czy_java.beautytips.database.user.UserDao
import com.blogspot.android_czy_java.beautytips.database.user.UserModel

class UserRepository(private val userDao: UserDao) {

    fun getUserByFirebaseId(firebaseId: String) = userDao.getUserByFirebaseId(firebaseId)

    fun insertUser(user: UserModel) = userDao.insertUser(user)
}