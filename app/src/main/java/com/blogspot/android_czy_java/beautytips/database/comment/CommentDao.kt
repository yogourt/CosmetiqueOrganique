package com.blogspot.android_czy_java.beautytips.database.comment

import androidx.room.*
import io.reactivex.Observable

@Dao
interface CommentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertComments(comments: List<CommentModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertComment(comment: CommentModel): Long

    @Query("SELECT * FROM RecipeComments WHERE recipeId = :recipeId ORDER BY firebaseId")
    fun getComments(recipeId: Long): Observable<List<CommentModel>>

    @Query("SELECT COUNT(recipeId) FROM RecipeComments WHERE recipeId = :recipeId")
    fun getCommentNumberForRecipe(recipeId: Long): Int

    @Query("SELECT * FROM RecipeComments WHERE id = :commentId")
    fun getCommentById(commentId: Long): CommentModel?

    @Query("SELECT * FROM RecipeComments WHERE firebaseId = null")
    fun getCommentsWithNullFirebaseId(): List<CommentModel>?
}