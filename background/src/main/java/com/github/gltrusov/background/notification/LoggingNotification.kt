package com.github.gltrusov.background.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.job.JobService.NOTIFICATION_SERVICE
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.github.gltrusov.background.R

//TODO Сделать Runtime permission для предоставления разрешения на отправку уведомлений в настройках

fun Context.createLoggingNotificationChannel() {
    val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val notificationChannel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(notificationChannel)
    }
}

fun Context.notifyLog(id: Int, text: String) {
    val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

    val notification = NotificationCompat.Builder(this, CHANNEL_ID)
        .setContentTitle("Log")
        .setContentText(text)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setOnlyAlertOnce(true)
        .build()

    notificationManager.notify(id, notification)
}


private const val CHANNEL_ID = "common_channel_id"
private const val CHANNEL_NAME = "common_channel_name"