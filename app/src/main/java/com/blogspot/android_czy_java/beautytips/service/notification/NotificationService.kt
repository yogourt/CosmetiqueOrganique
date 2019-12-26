package com.blogspot.android_czy_java.beautytips.service.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.blogspot.android_czy_java.beautytips.R
import com.blogspot.android_czy_java.beautytips.usecase.notification.ProcessNotificationUseCase
import com.blogspot.android_czy_java.beautytips.usecase.notification.SaveNotificationUseCase
import com.blogspot.android_czy_java.beautytips.view.recipe.MainActivity

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.android.AndroidInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class NotificationService :
        FirebaseMessagingService() {

    companion object {
        private const val CHANNEL_ID = "Comments notifications"
        const val KEY_ITEM = "recipe firebaseId"

    }

    @Inject
    lateinit var saveNotificationUseCase: SaveNotificationUseCase

    @Inject
    lateinit var processNotificationUseCase: ProcessNotificationUseCase

    private val disposable = CompositeDisposable()


    private var isChannelCreated = false

    override fun onCreate() {
        super.onCreate()
        AndroidInjection.inject(this)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        createNotificationChannel()

        saveNotificationUseCase.execute(remoteMessage)

        sendNotification(remoteMessage)

    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && !isChannelCreated) {
            isChannelCreated = true
            val channel = NotificationChannel(CHANNEL_ID,
                    "Cosmetique Organique notifications",
                    NotificationManager.IMPORTANCE_DEFAULT)
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
        }
    }

    private fun sendNotification(remoteMessage: RemoteMessage) {


        disposable.add(processNotificationUseCase.getMessage(
                remoteMessage.data)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe { message ->
                    val notification = createNotification(remoteMessage, message)

                    val manager = NotificationManagerCompat.from(applicationContext)
                    manager.notify(123, notification)

                } ?: return
        )


    }

    private fun createNotification(remoteMessage: RemoteMessage, message: String?): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.withoutback)
                .setContentTitle(processNotificationUseCase.getTitle(
                        remoteMessage.data[NotificationKeys.KEY_NOTIFICATION_TYPE]
                ))
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(createPendingIntent(remoteMessage))
                .setAutoCancel(true)
                .build()
    }

    private fun createPendingIntent(remoteMessage: RemoteMessage): PendingIntent? {
        val intent = Intent().setClass(this, MainActivity::class.java)
        val tipId = remoteMessage.data["tip"]
        intent.putExtra(KEY_ITEM, tipId)
        val uniqueInt = (System.currentTimeMillis() and 0xfffffff).toInt()
        return PendingIntent.getActivity(this, uniqueInt,
                intent, PendingIntent.FLAG_CANCEL_CURRENT)
    }

}
