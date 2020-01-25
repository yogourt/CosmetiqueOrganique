package com.blogspot.android_czy_java.beautytips.database.notification

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Observable

@Dao
interface NotificationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNotification(notification: NotificationModel)

    @Query("SELECT * FROM Notifications WHERE userId = :userId")
    fun getNotificationsForUser(userId: String): List<NotificationModel>

    @Query("UPDATE Notifications SET seen = 1 WHERE id = :notificationId")
    fun updateNotificationSeen(notificationId: Int)

    @Query("SELECT COUNT(id) FROM Notifications WHERE userId = :userId AND seen = 0")
    fun getUnreadNotificationNumberForUser(userId: String): Observable<Int>
}