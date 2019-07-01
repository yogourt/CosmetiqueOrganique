package com.blogspot.android_czy_java.beautytips.database.detail

import androidx.room.Dao
import androidx.room.Query

@Dao
interface RecipeDetailDao {

    @Query("SELECT Description FROM Recipes WHERE recipeId = :recipeId")
    fun getDescription(recipeId: Long): String

    @Query("SELECT Ingredients FROM Recipes WHERE recipeId = :recipeId")
    fun getIngredients(recipeId: Long): String

    @Query("SELECT Source FROM Recipes WHERE recipeId = :recipeId")
    fun getSource(recipeId: Long): String


}