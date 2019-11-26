package com.blogspot.android_czy_java.beautytips.database.notification

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NotificationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNotification(notification: NotificationModel)

    @Query("SELECT * FROM Notifications WHERE userId = :userId")
    fun getNotificationsForUser(userId: String): List<NotificationModel>

}