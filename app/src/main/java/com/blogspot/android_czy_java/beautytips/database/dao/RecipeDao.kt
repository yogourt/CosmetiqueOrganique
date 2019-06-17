package com.blogspot.android_czy_java.beautytips.database.dao

import androidx.room.*
import com.blogspot.android_czy_java.beautytips.database.models.RecipeModel
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipe(recipe: RecipeModel): Single<RecipeModel>

    @Query("DELETE FROM Recipes WHERE recipeId=:recipeId")
    fun deleteRecipe(recipeId: Long): Completable

    @Query("SELECT * FROM Recipes")
    fun getAllRecipes(): ArrayList<RecipeModel>

}