package com.github.gltrusov.background.services.alarm_manager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
import androidx.appcompat.app.AppCompatActivity
import com.github.gltrusov.background.databinding.AcrivityAlarmManagerBinding
import java.util.Calendar

class AlarmManagerActivity : AppCompatActivity() {

    private val binding by lazy {
        AcrivityAlarmManagerBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager


        binding.alarmManager.setOnClickListener {

            // С API 32 (Android 12) пользователь должен дать разрешение
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    if(alarmManager.canScheduleExactAlarms()) {
                        setAlarmManager(alarmManager)
                    } else {
                        startActivity(Intent(ACTION_REQUEST_SCHEDULE_EXACT_ALARM))
                    }
            } else {
                setAlarmManager(alarmManager)
            }

        }
        binding.markdown.loadMarkdownFile("file:///android_asset/alarm_manager.md")
    }

    private fun setAlarmManager(alarmManager: AlarmManager) {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.SECOND, 15)
        val intent = AlarmReceiver.newIntent(this)
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }


    internal companion object {
        fun start(context: Context): Intent =
            Intent(context, AlarmManagerActivity::class.java).apply {
                context.startActivity(this)
            }
    }

}