package com.example.todayiamdone

import android.annotation.SuppressLint
import android.app.IntentService
import android.app.Notification
import android.content.Intent
import android.os.Build

class NotificationService : IntentService("NotificationService") {
    private lateinit var mNotification: Notification
    private val mNotificationId: Int = 1000

    @SuppressLint("NewApi")
    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // TODO: 알림 채널 만들기 (API 26 이상부터 필요)
        }
    }

    companion object {
        const val CHANNEL_ID = "com.example.todayiamdone.notification"
        const val CHANNEL_NAME = "Today I am Done Notification"
    }

    override fun onHandleIntent(intent: Intent?) {
        // TODO: 채널 생성

        var timestamp: Long = 0

        // TODO: intent에서 메시지 받아오기

        if (timestamp > 0) {
            // TODO: 알림을 클릭했을 때 처리를 위해 필요한 데이터를 담는 Intent 생성

            // TODO: Pending Intent 생성 (안드로이드 시스템인 NotificationManager 사용할 때 이용)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // TODO: API 26 부터는 알림 채널을 이용하여 알림 생성
            } else {
                // TODO: API 26 이전에는 채널을 이용하지않고 알림 생성

            }

            // TODO: 알림 띄우기
        }
    }
}