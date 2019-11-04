package com.blogspot.android_czy_java.beautytips.usecase.detail

import com.blogspot.android_czy_java.beautytips.database.FirebaseKeys
import com.google.firebase.functions.FirebaseFunctions

class UpdateFavNumInFirebaseUseCase {

    fun execute(request: UpdateFavNumInFirebaseRequest) {

        val data = hashMapOf(
                FirebaseKeys.FUNCTION_KEY_RECIPE_ID to request.recipeId,
                FirebaseKeys.FUNCTION_KEY_INCREMENT to request.increment
        )
        FirebaseFunctions
                .getInstance()
                .getHttpsCallable(FirebaseKeys.FUNCTION_NAME_UPDATE_FAV_NUM)
                .call(data)

    }
}