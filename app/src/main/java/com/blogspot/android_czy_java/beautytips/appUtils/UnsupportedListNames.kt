package com.blogspot.android_czy_java.beautytips.appUtils

enum class UnsupportedListNames(private val label: String) {
    NICKNAME("nickname"),
    PHOTO("photo"),
    TOKEN("token");

    companion object {
        fun getLabels() = values().map { it.label }
    }
}