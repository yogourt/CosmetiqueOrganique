package com.blogspot.android_czy_java.beautytips.exception.account

class UserNotLoggedInException: Exception() {
    override val message: String?
        get() = "You are not logged in."

}