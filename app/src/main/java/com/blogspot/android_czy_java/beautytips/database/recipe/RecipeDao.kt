package com.blogspot.android_czy_java.beautytips.database.recipe

import androidx.lifecycle.LiveData
import androidx.room.*
import io.reactivex.Completable

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipe(recipe: RecipeModel)

    @Query("DELETE FROM Recipes WHERE recipeId=:recipeId")
    fun deleteRecipe(recipeId: Long): Completable

    @Query("SELECT * FROM Recipes")
    fun getAllRecipes(): LiveData<List<RecipeMappedModel>>

    @Query("SELECT FavNum FROM Recipes WHERE recipeId=:recipeId")
    fun getFavNum(recipeId: Long): LiveData<Long>



}