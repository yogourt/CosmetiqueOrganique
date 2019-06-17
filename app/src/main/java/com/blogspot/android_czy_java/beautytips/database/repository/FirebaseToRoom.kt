package com.blogspot.android_czy_java.beautytips.database.repository

import com.blogspot.android_czy_java.beautytips.database.exceptions.UserIsNullException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseException
import com.google.firebase.database.FirebaseDatabase

public class FirebaseToRoom
{

    //this method will be called only once, when app is launched for the first time and there is no data

    @Throws(UserIsNullException::class)
    fun observeFirebaseAndSaveToRoom()
    {
        val firebaseDatabase = FirebaseDatabase.getInstance()

        if(FirebaseAuth.getInstance().currentUser == null)
            throw UserIsNullException()

        firebaseDatabase.getReference("tipList")
                .addListenerForSingleValueEvent(TipListValueEventListener())

        firebaseDatabase.getReference("tips")
                .addListenerForSingleValueEvent(TipsValueEventListener())

        firebaseDatabase.getReference("ingredientList")
                .addListenerForSingleValueEvent(IngredientListValueEventListener())

        firebaseDatabase.getReference("ingredients")
                .addListenerForSingleValueEvent(IngredientsValueEventListener())
    }
}