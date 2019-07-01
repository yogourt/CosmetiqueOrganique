package com.blogspot.android_czy_java.beautytips.database.repository

import android.content.Context
import androidx.collection.LongSparseArray
import com.blogspot.android_czy_java.beautytips.database.AppDatabase
import com.blogspot.android_czy_java.beautytips.database.comment.CommentListConverter
import com.blogspot.android_czy_java.beautytips.database.comment.CommentModel
import com.blogspot.android_czy_java.beautytips.database.detail.DetailConverter
import com.blogspot.android_czy_java.beautytips.database.detail.RecipeDetailModel
import com.blogspot.android_czy_java.beautytips.database.recipe.RecipeConverter
import com.blogspot.android_czy_java.beautytips.database.recipe.RecipeModel
import com.google.firebase.database.DataSnapshot

class TipsValueEventListener(private val appContext: Context,
                             private val tipListDataSnapshot: DataSnapshot) : RepositoryValueEventListener(appContext) {


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

            AppDatabase.getInstance(appContext).commentDao().insertComments(comments)

            for (item in tipListDataSnapshot.children) {

                val recipeToInsert = RecipeConverter(item).getRecipe() ?: continue
                val recipeId = recipeToInsert.recipeId

                val details = tipDetailsMap[recipeId] ?: continue
                recipeToInsert.details = details

                AppDatabase.getInstance(appContext).recipeDao().insertRecipe(recipeToInsert)
            }

        }.run()

    }
}