package com.blogspot.android_czy_java.beautytips.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Ingredients")
data class IngredientModel(
        @PrimaryKey
        val ingredientId: Long,
        val title: String,
        val image: String,
        val category: String,
        val tags: String,

        val overview: String,
        val forFace: String,
        val forHair: String,
        val forBody: String)