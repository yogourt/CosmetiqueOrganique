package com.blogspot.android_czy_java.beautytips.exception.detail

class ListAlreadyExistsException: Throwable() {
    override val message: String?
        get() = "List with this title already exists"
}