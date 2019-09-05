package com.blogspot.android_czy_java.beautytips.exception.common

class DatabaseIsEmptyException: Exception() {
    override val message: String?
        get() = "Database is empty"
}