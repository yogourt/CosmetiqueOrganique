package com.blogspot.android_czy_java.beautytips.database.recipe

import androidx.lifecycle.LiveData
import androidx.room.*
import com.blogspot.android_czy_java.beautytips.appUtils.categories.labels.CategoryLabel
import io.reactivex.Completable

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipe(recipe: RecipeModel)

    @Query("DELETE FROM Recipes WHERE recipeId=:recipeId")
    fun deleteRecipe(recipeId: Long): Completable

    @Query("SELECT * FROM Recipes")
    fun getAllRecipes(): List<RecipeMappedModel>

    @Query("SELECT * FROM Recipes WHERE category=:category")
    fun getRecipesByCategory(category: String): List<RecipeMappedModel>

    @Query("SELECT * FROM Recipes WHERE category=:category AND subcategory=:subcategory")
    fun getRecipesByCategoryAndSubcategory(category: String, subcategory: String): List<RecipeMappedModel>

    @Query("SELECT * FROM Recipes ORDER BY favNum DESC")
    fun getAllRecipesOrderByPopularity(): List<RecipeMappedModel>

    @Query("SELECT * FROM Recipes WHERE category=:category ORDER BY favNum DESC")
    fun getRecipesByCategoryOrderByPopularity(category: String): List<RecipeMappedModel>

    @Query("SELECT * FROM Recipes WHERE category=:category AND subcategory=:subcategory ORDER BY favNum DESC")
    fun getRecipesByCategoryAndSubcategoryOrderByPopularity(category: String, subcategory: String): List<RecipeMappedModel>

    @Query("SELECT FavNum FROM Recipes WHERE recipeId=:recipeId")
    fun getFavNum(recipeId: Long): LiveData<Long>

}