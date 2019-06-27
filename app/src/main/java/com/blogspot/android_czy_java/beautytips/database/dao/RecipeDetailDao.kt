package com.blogspot.android_czy_java.beautytips.database.dao

import androidx.room.Dao
import androidx.room.Query

@Dao
interface RecipeDetailDao {

    @Query("SELECT Description FROM Recipes WHERE recipeId = :recipeId")
    fun getDescription(recipeId: Long): String

    @Query("SELECT Ingredients FROM Recipes WHERE recipeId = :recipeId")
    fun getIngredients(recipeId: Long): String


}