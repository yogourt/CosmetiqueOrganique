package com.blogspot.android_czy_java.beautytips.database.repository

import androidx.collection.LongSparseArray
import com.blogspot.android_czy_java.beautytips.database.AppDatabase
import com.blogspot.android_czy_java.beautytips.database.comment.CommentListConverter
import com.blogspot.android_czy_java.beautytips.database.comment.CommentModel
import com.blogspot.android_czy_java.beautytips.database.detail.DetailConverter
import com.blogspot.android_czy_java.beautytips.database.detail.RecipeDetailModel
import com.blogspot.android_czy_java.beautytips.database.recipe.RecipeConverter
import com.blogspot.android_czy_java.beautytips.database.recipe.RecipeModel
import com.google.firebase.database.DataSnapshot
import io.reactivex.SingleEmitter

class TipsValueEventListener(private val database: AppDatabase,
                             private val tipListDataSnapshot: DataSnapshot,
                             private val emitter: SingleEmitter<Boolean>) : RepositoryValueEventListener(emitter) {


    override fun onDataChange(tipsDataSnapshot: DataSnapshot) {

        Runnable {

            val comments = ArrayList<CommentModel>()
            val tipDetailsMap = LongSparseArray<RecipeDetailModel>()

            for (item in tipsDataSnapshot.children) {

                val id = item.key ?: continue
                val recipeId = id.toLong()

                comments.addAll(CommentListConverter(item, recipeId).getComments())

                tipDetailsMap.append(recipeId, DetailConverter(item).getDetails())

            }

            database.commentDao().insertComments(comments)

            val recipes = ArrayList<RecipeModel>()
            for (item in tipListDataSnapshot.children) {

                val recipeToInsert = RecipeConverter(item).getRecipe() ?: continue
                val recipeId = recipeToInsert.recipeId

                val details = tipDetailsMap[recipeId] ?: continue
                recipeToInsert.details = details

                recipes.add(recipeToInsert)

            }

            database.recipeDao().insertRecipes(recipes)

            emitter.onSuccess(true)

        }.run()

    }
}