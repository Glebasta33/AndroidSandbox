package com.github.gltrusov.background.services.job_intent_service

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.JobIntentService
import com.github.gltrusov.background.notification.createLoggingNotificationChannel
import com.github.gltrusov.background.notification.notifyLog

/**
 * Для организации последовательного выполнения
 * сервисов (в очереди) используются:
 * - С API 26 - JobService.
 * - До API 26 - IntentService.
 *
 * Но чтобы не делать проверку версии API можно
 * использовать JobIntentService, который делает
 * эту проверку автоматически.
 *
 * С API 26 запускается JobService через JobScheduler.
 * До API 26 запускается IntentService через Context.startIntent.
 *
 * Но для JobIntentService нельзя устанавливать ограничения,
 * как в JobIntent (подключение к WiFi и т.д.)
 */
class MyJobIntentService : JobIntentService() {

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
        createLoggingNotificationChannel()
    }

    /**
     * Метод аналогичен методу onHandleIntent
     */
    override fun onHandleWork(intent: Intent) {
        log("onHandleIntent")
        val page = intent.getIntExtra(PAGE, 0)
        for (i in 0 until 5) {
            Thread.sleep(1000)
            log("page $page loading ${i * 10} %")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
    }

    private fun log(message: String) {
        val text = "MyJobIntentService: $message"
        Log.d("MyLog", text)
        notifyLog(15, text)
    }

    companion object {

        private const val PAGE = "page"
        private const val JOB_ID = 111

        /**
         * Запускается JobIntentService через
         * вызов статического метода enqueueWork
         */
        fun enqueue(context: Context, page: Int) {
            JobIntentService.enqueueWork(
                context,
                MyJobIntentService::class.java,
                JOB_ID,
                newIntent(context, page)
            )
        }

        private fun newIntent(context: Context, page: Int): Intent {
            return Intent(context, MyJobIntentService::class.java).apply {
                putExtra(PAGE, page)
            }
        }
    }
}