package com.blogspot.android_czy_java.beautytips.database

interface FirebaseKeys {

    companion object {
        const val KEY_RECIPE_DESCRIPTION = "description"
        const val KEY_RECIPE_INGREDIENTS = "ingredients"
        const val KEY_RECIPE_SOURCE = "source"

        const val KEY_COMMENT_MESSAGE = "c"
        const val KEY_COMMENT_AUTHOR = "a"
        const val KEY_COMMENT_AUTHOR_ID = "b"
    }
}