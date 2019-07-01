package com.blogspot.android_czy_java.beautytips.database.comment

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CommentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertComments(comments: List<CommentModel>)

    @Query("SELECT * FROM RecipeComments WHERE recipeId=:recipeId ")
    fun getCommentsForRecipe(recipeId: Long): List<CommentModel>

}