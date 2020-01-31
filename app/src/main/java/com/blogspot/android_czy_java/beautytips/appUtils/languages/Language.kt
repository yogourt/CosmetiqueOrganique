package com.blogspot.android_czy_java.beautytips.appUtils.languages

enum class Language(val label: String, val code: String) {

    ENGLISH("English", "en"),
    FRENCH("French", "fr"),
    SPANISH("Spanish", "es"),
    POLISH("Polish", "pl");

    fun getCode(language: String) = values().first { label == language }.code

}