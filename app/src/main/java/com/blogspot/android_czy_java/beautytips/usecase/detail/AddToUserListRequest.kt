package com.blogspot.android_czy_java.beautytips.usecase.detail

class AddToUserListRequest(val recipeId: Long, var listName: String) {
    init {
        listName = listName.toLowerCase().replace(" ", "_")
    }
}