package com.github.gltrusov.background.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.github.gltrusov.background.R
import com.github.gltrusov.background.databinding.ActivityNotificationAboutBinding

class NotificationAboutActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityNotificationAboutBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.basicNotification.setOnClickListener {
            showNotification()
        }
        binding.notificationWithChannel.setOnClickListener {
            showNotificationWithChannel()
        }
    }

    private fun showNotification() {
        val notification = Notification.Builder(this)
            .setContentTitle("Title")
            .setContentText("Notification")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, notification)
    }

    private fun showNotificationWithChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                "CHANNEL_NAME",
                NotificationManager.IMPORTANCE_HIGH
            )

            notificationManager.createNotificationChannel(notificationChannel)

            val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Title")
                .setContentText("Notification with channel")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .build()

            notificationManager.notify(2, notification)
        }
    }

    internal companion object {
        private const val CHANNEL_ID = "CHANNEL_ID"

        fun start(context: Context) =
            Intent(context, NotificationAboutActivity::class.java).apply {
                context.startActivity(this)
            }
    }
}