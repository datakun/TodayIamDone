package com.example.todayiamdone

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import java.util.*

class NotificationService : IntentService("NotificationService") {
    private lateinit var mNotification: Notification
    private val mNotificationId: Int = 1000

    @SuppressLint("NewApi")
    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val context = this.applicationContext
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance)
            notificationChannel.enableVibration(true)
            notificationChannel.setShowBadge(true)
            notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    companion object {
        const val CHANNEL_ID = "com.example.todayiamdone.notification"
        const val CHANNEL_NAME = "Today I am Done Notification"
    }

    override fun onHandleIntent(intent: Intent?) {
        createChannel()

        var timestamp: Long = 0
        var todo = ""
        var message = "할 일은 다 끝냈나요?"
        if (intent != null && intent.extras != null) {
            timestamp = intent.extras!!.getLong("timestamp")
            todo = intent.extras!!.getString("todo")
        }

        if (timestamp > 0) {
            val context = this.applicationContext
            var notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notifyIntent = Intent(this, TodoActivity::class.java)

            notifyIntent.putExtra("title", todo)
            notifyIntent.putExtra("message", message)
            notifyIntent.putExtra("notification", true)

            notifyIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

            val pendingIntent = PendingIntent.getActivity(
                context,
                0,
                notifyIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            val res = this.resources
            val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mNotification = Notification.Builder(this, CHANNEL_ID)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.ic_baseline_playlist_add_check_24)
                    .setAutoCancel(true)
                    .setContentTitle(todo)
                    .setStyle(
                        Notification.BigTextStyle()
                            .bigText(message)
                    )
                    .setContentText(message).build()
            } else {
                mNotification = Notification.Builder(this)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.ic_baseline_playlist_add_check_24)
                    .setLargeIcon(BitmapFactory.decodeResource(res, R.mipmap.ic_launcher))
                    .setAutoCancel(true)
                    .setPriority(Notification.PRIORITY_MAX)
                    .setContentTitle(todo)
                    .setStyle(
                        Notification.BigTextStyle()
                            .bigText(message)
                    )
                    .setSound(uri)
                    .setContentText(message).build()

            }

            notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(mNotificationId, mNotification)
        }


    }
}