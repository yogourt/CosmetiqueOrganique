package com.blogspot.android_czy_java.beautytips.database.models

import androidx.annotation.Nullable
import androidx.room.Entity
import androidx.room.Relation

@Entity
data class RecipeDetailModel (
    var description: String,
    var source: String? = null,
    var commentNum: Long = 0,
    var ingredients: String
)