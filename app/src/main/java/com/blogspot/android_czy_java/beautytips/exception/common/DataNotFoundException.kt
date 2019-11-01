package com.blogspot.android_czy_java.beautytips.exception.common

import java.lang.Exception

class DataNotFoundException: Exception() {
    override val message: String?
        get() = "Data was not found"
}