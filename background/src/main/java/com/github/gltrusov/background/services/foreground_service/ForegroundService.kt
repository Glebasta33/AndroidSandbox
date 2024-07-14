package com.github.gltrusov.background.services.foreground_service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.github.gltrusov.background.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

internal class ForegroundService : Service() {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private lateinit var notificationManager: NotificationManager

    @RequiresApi(Build.VERSION_CODES.ECLAIR)
    override fun onCreate() {
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        super.onCreate()
        log("onCreate")
        createNotificationChannel()
        // единственное, что отличает ForegroundService от обычного сервиса - необходимо вызвать startForeground:
        startForeground(NOTIFICATION_ID, createNotification("Service is working"))
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        log("onStartCommand")
        coroutineScope.launch {
            for (i in 0 until 100) {
                delay(1000)
                log("Timer $i")
                notificationManager.notify(
                    NOTIFICATION_ID,
                    createNotification("Service is working with ${intent?.getStringExtra(START_SERVICE_CONSTANT)}: $i")
                )
            }
            stopSelf()
        }
        if (intent != null) {
            return when (intent.getStringExtra(START_SERVICE_CONSTANT)) {
                "START_NOT_STICKY" -> START_NOT_STICKY
                "START_STICKY" -> START_STICKY
                "START_REDELIVER_INTENT" -> START_REDELIVER_INTENT
                else -> super.onStartCommand(intent, flags, startId)
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
        log("onDestroy")
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    private fun log(message: String) {
        Log.d("SERVICE_TAG", "MyForegroundService: $message")
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun createNotification(text: String) = NotificationCompat.Builder(this, CHANNEL_ID)
        .setContentTitle("Foreground Service")
        .setContentText(text)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setSilent(true)
        .build()

    companion object {

        private const val CHANNEL_ID = "channel_id"
        private const val CHANNEL_NAME = "channel_name"
        private const val NOTIFICATION_ID = 1

        private const val START_SERVICE_CONSTANT = "START_SERVICE_CONSTANT"
        fun newIntent(context: Context, constant: String): Intent {
            return Intent(context, ForegroundService::class.java).apply {
                putExtra(START_SERVICE_CONSTANT, constant)
            }
        }
    }
}