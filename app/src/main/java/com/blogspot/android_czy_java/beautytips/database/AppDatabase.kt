package com.blogspot.android_czy_java.beautytips.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.blogspot.android_czy_java.beautytips.database.dao.ErrorDao
import com.blogspot.android_czy_java.beautytips.database.dao.RecipeDao
import com.blogspot.android_czy_java.beautytips.database.dao.RecipeDetailDao
import com.blogspot.android_czy_java.beautytips.database.models.ErrorModel
import com.blogspot.android_czy_java.beautytips.database.models.IngredientModel
import com.blogspot.android_czy_java.beautytips.database.models.RecipeModel

@Database(entities = arrayOf(
        RecipeModel::class,
        IngredientModel::class,
        ErrorModel::class),
        version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun recipeDao(): RecipeDao
    abstract fun recipeDetailDao(): RecipeDetailDao
    abstract fun errorDao(): ErrorDao

    companion object {

        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        AppDatabase::class.java, "CosmetiqueOrganique.db")
                        .build()
    }

}