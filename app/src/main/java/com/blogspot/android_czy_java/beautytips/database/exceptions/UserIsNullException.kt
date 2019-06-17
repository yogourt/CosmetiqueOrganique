package com.blogspot.android_czy_java.beautytips.database.exceptions

class UserIsNullException: Throwable() {

    override val message: String?
        get() = "Firebase user is null"
}