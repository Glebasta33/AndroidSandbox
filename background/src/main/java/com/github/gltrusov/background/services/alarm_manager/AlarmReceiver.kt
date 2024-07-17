package com.github.gltrusov.background.services.alarm_manager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.github.gltrusov.background.notification.createLoggingNotificationChannel
import com.github.gltrusov.background.notification.notifyLog

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        context.createLoggingNotificationChannel()
        context.notifyLog(1, "AlarmReceiver")
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, AlarmReceiver::class.java)
        }
    }
}