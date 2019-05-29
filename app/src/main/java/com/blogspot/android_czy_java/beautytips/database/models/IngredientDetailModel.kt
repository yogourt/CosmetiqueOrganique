package com.blogspot.android_czy_java.beautytips.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="IngredientDetails")
data class IngredientDetailModel(
        @PrimaryKey
        val ingredientId: Long,
        val overview: String,
        val forFace: String,
        val forHair: String,
        val forBody: String)