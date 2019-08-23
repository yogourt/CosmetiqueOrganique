package com.blogspot.android_czy_java.beautytips.database.recipe

import androidx.lifecycle.LiveData
import androidx.room.*
import io.reactivex.Completable

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipes(recipes: List<RecipeModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipe(recipe: RecipeModel)

    @Query("DELETE FROM Recipes WHERE recipeId=:recipeId")
    fun deleteRecipe(recipeId: Long): Completable

    @Query("SELECT * FROM Recipes")
    fun getAllRecipes(): List<RecipeModel>

    @Query("SELECT recipeId FROM Recipes")
    fun getAllRecipesIds(): List<Long>

    @Query("SELECT * FROM Recipes WHERE category=:category")
    fun getRecipesByCategory(category: String): List<RecipeModel>

    @Query("SELECT * FROM Recipes WHERE category=:category AND subcategory=:subcategory")
    fun getRecipesByCategoryAndSubcategory(category: String, subcategory: String): List<RecipeModel>

    @Query("SELECT * FROM Recipes ORDER BY favNum")
    fun getAllRecipesOrderByPopularity(): List<RecipeModel>

    @Query("SELECT * FROM Recipes WHERE category=:category ORDER BY favNum")
    fun getRecipesByCategoryOrderByPopularity(category: String): List<RecipeModel>

    @Query("SELECT * FROM Recipes WHERE category=:category AND subcategory=:subcategory ORDER BY favNum")
    fun getRecipesByCategoryAndSubcategoryOrderByPopularity(category: String, subcategory: String): List<RecipeModel>

    @Query("SELECT FavNum FROM Recipes WHERE recipeId=:recipeId")
    fun getFavNum(recipeId: Long): LiveData<Long>

    @Query("SELECT Category FROM Recipes WHERE recipeId=:recipeId")
    fun getCategory(recipeId: Long): String

    @Query("SELECT Subcategory FROM Recipes WHERE recipeId=:recipeId")
    fun getSubcategory(recipeId: Long): String

}