package com.blogspot.android_czy_java.beautytips.database.notification

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.DateFormat

@Entity(tableName = "Notifications")
class NotificationModel
(
        val authorId: String?,
        var message: String,
        val image: String?,
        val recipeId: Long?,
        val commentId: String?,
        val userId: String
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
    var seen = false
    var timeInMillis = System.currentTimeMillis()
    var time = DateFormat.getDateInstance().format(timeInMillis)
}