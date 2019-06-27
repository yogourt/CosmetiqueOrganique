package com.blogspot.android_czy_java.beautytips.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.blogspot.android_czy_java.beautytips.database.models.RecipeModel
import com.blogspot.android_czy_java.beautytips.database.models.mapped.RecipeMappedModel
import io.reactivex.Completable
import io.reactivex.Single

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