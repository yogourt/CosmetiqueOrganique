package com.blogspot.android_czy_java.beautytips.repository.forViewModels.user

import com.blogspot.android_czy_java.beautytips.database.error.ErrorModel
import com.blogspot.android_czy_java.beautytips.database.user.UserModel
import com.blogspot.android_czy_java.beautytips.database.userlist.UserListModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import io.reactivex.SingleEmitter

class UserValueEventListener(private val emitter: SingleEmitter<UserModel>,
                             private val repository: UserRepository,
                             private val userId: String) : ValueEventListener {

    override fun onDataChange(userSnapshot: DataSnapshot) {

        Thread(Runnable {

            var nickname = userSnapshot.child("nickname").value ?: ""
            nickname = nickname.toString()

            var photo = userSnapshot.child("photo").value ?: ""
            photo = photo.toString()

            val user = UserModel(userId, nickname, photo)
            repository.insertUser(user)

            insertUserList("favorites", userSnapshot)
            insertUserList("my_recipes", userSnapshot)

            emitter.onSuccess(user)

        }).start()

    }

    override fun onCancelled(databaseError: DatabaseError) {
        emitter.onError(ErrorModel(databaseError.message, databaseError.code))
    }

    private fun insertUserList(listName: String, userSnapshot: DataSnapshot) {
        val favorites = userSnapshot.child(listName).value
        favorites?.let {
            val list = UserListModel(userId, listName, favorites.toString())
            repository.insertUserList(list)
        }
    }
}