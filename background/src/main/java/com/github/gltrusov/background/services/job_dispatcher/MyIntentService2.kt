package com.github.gltrusov.background.services.job_dispatcher

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.util.Log
import com.github.gltrusov.background.notification.createLoggingNotificationChannel
import com.github.gltrusov.background.notification.notifyLog

/**
 *  Альтернатива JobService при API ниже 26
 */
class MyIntentService2 : IntentService(NAME) {

    override fun onCreate() {
        super.onCreate()
        log("onCreate")
        createLoggingNotificationChannel()
        setIntentRedelivery(true)
    }

    override fun onHandleIntent(intent: Intent?) {
        log("onHandleIntent")
        val page = intent?.getIntExtra(PAGE, 0) ?: 0
        for (i in 0 until 5) {
            Thread.sleep(1000)
            log("page $page loading ${i * 10} %\"")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
    }

    private fun log(message: String) {
        val text = "IntentService: $message"
        Log.d("SERVICE_TAG", text)
        notifyLog(14, text)
    }

    companion object {

        private const val NAME = "MyIntentService"
        private const val PAGE = "page"

        fun newIntent(context: Context, page: Int): Intent {
            return Intent(context, MyIntentService2::class.java).apply {
                putExtra(PAGE, page)
            }
        }
    }
}