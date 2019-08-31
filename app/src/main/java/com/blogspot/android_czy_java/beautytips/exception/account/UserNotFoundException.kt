package com.blogspot.android_czy_java.beautytips.exception.account

class UserNotFoundException: Exception() {

    override val message: String?
        get() = "User was not found in database."
}