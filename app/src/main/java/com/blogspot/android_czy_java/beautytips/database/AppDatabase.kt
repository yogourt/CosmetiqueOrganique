package com.blogspot.android_czy_java.beautytips.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.blogspot.android_czy_java.beautytips.database.comment.CommentDao
import com.blogspot.android_czy_java.beautytips.database.error.ErrorDao
import com.blogspot.android_czy_java.beautytips.database.recipe.RecipeDao
import com.blogspot.android_czy_java.beautytips.database.detail.DetailDao
import com.blogspot.android_czy_java.beautytips.database.comment.CommentModel
import com.blogspot.android_czy_java.beautytips.database.error.ErrorModel
import com.blogspot.android_czy_java.beautytips.database.recipe.RecipeModel
import com.blogspot.android_czy_java.beautytips.database.user.UserDao
import com.blogspot.android_czy_java.beautytips.database.user.UserModel
import com.blogspot.android_czy_java.beautytips.database.userlist.UserListDao
import com.blogspot.android_czy_java.beautytips.database.userlist.UserListModel

@Database(entities = arrayOf(
        RecipeModel::class,
        CommentModel::class,
        ErrorModel::class,
        UserModel::class,
        UserListModel::class),
        version = 11,
        exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun recipeDao(): RecipeDao
    abstract fun recipeDetailDao(): DetailDao
    abstract fun commentDao(): CommentDao
    abstract fun errorDao(): ErrorDao
    abstract fun userDao(): UserDao
    abstract fun userListDao(): UserListDao

    companion object {

        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        AppDatabase::class.java, "CosmetiqueOrganique.db")
                        .fallbackToDestructiveMigration()
                        .build()
    }

}