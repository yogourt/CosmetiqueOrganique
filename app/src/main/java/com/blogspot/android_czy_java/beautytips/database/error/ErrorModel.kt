package com.blogspot.android_czy_java.beautytips.database.error

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ErrorModel(
       override val message: String,
        val code: Int
): Throwable() {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}