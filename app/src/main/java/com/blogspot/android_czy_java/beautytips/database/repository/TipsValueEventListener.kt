package com.blogspot.android_czy_java.beautytips.database.repository

import android.content.Context
import com.blogspot.android_czy_java.beautytips.database.AppDatabase
import com.blogspot.android_czy_java.beautytips.database.comment.CommentListConverter
import com.blogspot.android_czy_java.beautytips.database.comment.CommentModel
import com.blogspot.android_czy_java.beautytips.database.detail.DetailConverter
import com.blogspot.android_czy_java.beautytips.database.detail.RecipeDetailModel
import com.blogspot.android_czy_java.beautytips.database.recipe.RecipeModel
import com.google.firebase.database.DataSnapshot

class TipsValueEventListener(private val appContext: Context,
                             private val tipListDataSnapshot: DataSnapshot) : RepositoryValueEventListener(appContext) {


    override fun onDataChange(tipsDataSnapshot: DataSnapshot) {

        Runnable {

            val comments = ArrayList<CommentModel>()
            val tipDetailsMap: HashMap<String, RecipeDetailModel> = HashMap()

            for (item in tipsDataSnapshot.children) {

                val recipeId = item.key ?: continue

                comments.addAll(CommentListConverter(item, recipeId.toLong()).getComments())

                tipDetailsMap[recipeId] = DetailConverter(item).getDetails()

            }

            AppDatabase.getInstance(appContext).commentDao().insertComments(comments)

            for (item in tipListDataSnapshot.children) {

                val recipeId = item.key ?: continue
                val title = item.child("title").value.toString()
                val image = item.child("image").value.toString()
                val authorId = item.child("authorId").value
                val category = item.child("category").value.toString()
                val subcategory = item.child("subcategory").value.toString()
                val favNum = item.child("favNum").value.toString()
                val tags = item.child("tags").value.toString()
                val details = tipDetailsMap[recipeId] ?: continue

                var recipeToInsert: RecipeModel

                if(authorId != null) {
                    recipeToInsert = RecipeModel(
                            recipeId.toLong(),
                            title,
                            image,
                            authorId.toString(),
                            category,
                            subcategory,
                            favNum.toLong(),
                            tags,
                            details)
                } else {
                    recipeToInsert = RecipeModel(
                            recipeId.toLong(),
                            title,
                            image,
                            null,
                            category,
                            subcategory,
                            favNum.toLong(),
                            tags,
                            details)
                }

                AppDatabase.getInstance(appContext).recipeDao().insertRecipe(recipeToInsert)
            }

        }.run()

    }
}