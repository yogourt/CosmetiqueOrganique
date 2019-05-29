package com.blogspot.android_czy_java.beautytips.database.dao

import androidx.room.Query
import com.blogspot.android_czy_java.beautytips.database.models.RecipeDetailModel
import io.reactivex.Observable

interface RecipeDetailDao {

    @Query("SELECT * FROM RecipeDetails WHERE recipeId=:recipeId")
    fun getRecipeDetail(recipeId: Long): Observable<RecipeDetailModel>
}