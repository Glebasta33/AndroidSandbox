package com.github.gltrusov.background.services.job_service

import android.app.job.JobParameters
import android.app.job.JobService
import android.os.PersistableBundle
import android.util.Log
import com.github.gltrusov.background.notification.createLoggingNotificationChannel
import com.github.gltrusov.background.notification.notifyLog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class JobServiceWithParam : JobService() {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()
        createLoggingNotificationChannel()
        log("onCreate")
    }

    /**
     * Если запустить JobService с новыми параметрами чезез schedule,
     * старый сервис отменится и запустится новый.
     */
    override fun onStartJob(params: JobParameters?): Boolean {
        log("onStartJob")
        val page = params?.extras?.getInt(PAGE) ?: 0

        coroutineScope.launch {
            for (i in 0 until 10) {
                delay(1000)
                log("JobService: page $page loading ${i * 10} %")
            }
            jobFinished(params, true)
        }
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        log("onStopJob")
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
        log("onDestroy")
    }

    private fun log(message: String) {
        Log.d("MyLog", "MyJobService: $message")
        notifyLog(11, message)
    }

    companion object {
        const val JOB_ID = 112
        private const val PAGE = "page"

        fun newBundle(page: Int) = PersistableBundle().apply {
            putInt(PAGE, page)
        }
    }
}