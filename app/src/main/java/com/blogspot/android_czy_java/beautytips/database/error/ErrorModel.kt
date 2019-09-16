package com.blogspot.android_czy_java.beautytips.database.error

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Errors")
data class ErrorModel(
        val tableName: String,
        val rowId: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}