package com.blogspot.android_czy_java.beautytips.view.listView.model

class User {

    companion object {
        const val USER_STATE_LOGGED_IN = "logged_in"
        const val USER_STATE_ANONYMOUS = "anonymous"
        const val USER_STATE_NULL = "null"
    }

    var photo: String = ""
    var nickname: String = ""
    var userState = USER_STATE_NULL

}