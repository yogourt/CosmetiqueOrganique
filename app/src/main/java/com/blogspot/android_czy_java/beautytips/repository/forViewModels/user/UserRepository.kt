package com.blogspot.android_czy_java.beautytips.repository.forViewModels.user

import com.blogspot.android_czy_java.beautytips.database.user.UserDao
import com.blogspot.android_czy_java.beautytips.database.user.UserModel
import com.blogspot.android_czy_java.beautytips.database.userlist.UserListDao
import com.blogspot.android_czy_java.beautytips.database.userlist.UserListModel
import com.google.firebase.database.FirebaseDatabase
import io.reactivex.SingleEmitter

class UserRepository(private val userDao: UserDao,
                     private val userListDao: UserListDao) {

    fun getUserByFirebaseId(firebaseId: String) = userDao.getUserByFirebaseId(firebaseId)

    fun insertUser(user: UserModel) = userDao.insertUser(user)

    fun insertUserList(list: UserListModel) = userListDao.insertList(list)

    fun insertFirebaseUser(firebaseId: String,
                           emitter: SingleEmitter<UserModel>,
                           insertList: Boolean) {

            FirebaseDatabase.getInstance().getReference("users/$firebaseId")
                    .addListenerForSingleValueEvent(
                            UserValueEventListener(
                                    emitter,
                                    this,
                                    firebaseId,
                                    insertList
                            ))
    }

    fun updateUser(user: UserModel) = userDao.updateUser(user)
}