package com.blogspot.android_czy_java.beautytips.repository

import com.blogspot.android_czy_java.beautytips.database.user.UserModel
import com.blogspot.android_czy_java.beautytips.database.userlist.UserListModel
import com.blogspot.android_czy_java.beautytips.exception.common.FirebaseValueEventListenerException
import com.blogspot.android_czy_java.beautytips.repository.forViewModels.user.UserRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import io.reactivex.SingleEmitter

class UserValueEventListener(private val emitter: SingleEmitter<UserModel>,
                             private val repository: UserRepository,
                             private val userId: String,
                             private val insertList: Boolean) : ValueEventListener {

    override fun onDataChange(userSnapshot: DataSnapshot) {

        Thread(Runnable {

            var nickname = userSnapshot.child("nickname").value ?: ""
            nickname = nickname.toString()

            var photo = userSnapshot.child("photo").value ?: ""
            photo = photo.toString()

            val user = UserModel(userId, nickname, photo)
            repository.insertUser(user)

            if(insertList) {
                insertUserList("favorites", userSnapshot)
                insertUserList("my_recipes", userSnapshot)
            }

            emitter.onSuccess(user)


        }).start()

    }

    override fun onCancelled(databaseError: DatabaseError) {
        emitter.onError(FirebaseValueEventListenerException(databaseError.message))
    }

    private fun insertUserList(listName: String, userSnapshot: DataSnapshot) {
        val favorites = userSnapshot.child(listName).value
        favorites?.let {
            val listNameRefactored = listName.replace("_", " ").capitalize()
            val list = UserListModel(userId, listNameRefactored, favorites.toString())
            repository.insertUserList(list)
        }
    }
}