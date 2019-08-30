package com.blogspot.android_czy_java.beautytips.repository.forViewModels.user

import com.blogspot.android_czy_java.beautytips.database.user.UserDao

class UserRepository(private val userDao: UserDao) {

    fun getCurrentUser() = userDao.getUsers().firstOrNull { it.isLoggedIn }

}