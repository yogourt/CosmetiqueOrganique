package com.blogspot.android_czy_java.beautytips.usecase.account.userlist

import com.blogspot.android_czy_java.beautytips.appUtils.UnsupportedListNames
import com.blogspot.android_czy_java.beautytips.database.userlist.UserListModel
import com.google.firebase.database.FirebaseDatabase

class PushUserListToFirebaseUseCase {

    fun execute(list: UserListModel) {

        if (isListNameSupported(list.listName)) {
            FirebaseDatabase
                    .getInstance()
                    .getReference("users/${list.userId}/${list.listName}")
                    .setValue(list.recipesInList)
        }
    }

    private fun isListNameSupported(name: String) =
            !UnsupportedListNames.getLabels().contains(name.toLowerCase())

}