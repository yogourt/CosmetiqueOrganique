package com.blogspot.android_czy_java.beautytips.service.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.blogspot.android_czy_java.beautytips.R
import com.blogspot.android_czy_java.beautytips.usecase.notification.SaveNotificationUseCase
import com.blogspot.android_czy_java.beautytips.view.recipe.MainActivity

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.android.AndroidInjection
import javax.inject.Inject


class NotificationService :
        FirebaseMessagingService() {

    companion object {
        private const val CHANNEL_ID = "Comments notifications"
        const val KEY_ITEM = "recipe firebaseId"

    }

    @Inject
    lateinit var saveNotificationUseCase: SaveNotificationUseCase

    private var isChannelCreated = false

    override fun onCreate() {
        super.onCreate()
        AndroidInjection.inject(this)
    }

    override fun onNewToken(token: String) {
        //TODO:
        //FirebaseDatabase.getInstance().getReference("userTokens").child(
        //          FirebaseLoginHelper.getUserId()).setValue(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        createNotificationChannel()

        saveNotificationUseCase.execute(remoteMessage)

        val intent = Intent().setClass(this, MainActivity::class.java)
        val tipId = remoteMessage.data["tip"]
        intent.putExtra(KEY_ITEM, tipId)
        val uniqueInt = (System.currentTimeMillis() and 0xfffffff).toInt()
        val pendingIntent = PendingIntent.getActivity(this, uniqueInt,
                intent, PendingIntent.FLAG_CANCEL_CURRENT)

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.withoutback)
                .setContentTitle(remoteMessage.data["title"])
                .setContentText(remoteMessage.data["body"])
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()

        val manager = NotificationManagerCompat.from(applicationContext)
        manager.notify(123, notification)

    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && !isChannelCreated) {
            isChannelCreated = true
            val channel = NotificationChannel(CHANNEL_ID,
                    "Cosmetique Organique notifications",
                    NotificationManager.IMPORTANCE_DEFAULT)
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
        }
    }

}
