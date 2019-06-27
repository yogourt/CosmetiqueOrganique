package com.blogspot.android_czy_java.beautytips.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ErrorModel(
        val message: String,
        val code: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}