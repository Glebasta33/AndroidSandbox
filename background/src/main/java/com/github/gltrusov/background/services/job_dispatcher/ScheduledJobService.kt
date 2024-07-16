package com.github.gltrusov.background.services.job_dispatcher

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.os.Build
import android.util.Log
import com.github.gltrusov.background.notification.createLoggingNotificationChannel
import com.github.gltrusov.background.notification.notifyLog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ScheduledJobService : JobService() {

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            coroutineScope.launch {
                var workItem = params?.dequeueWork() // берём первый элемент очереди
                // проходимся по очереди
                while (workItem != null) {
                    val page = workItem.intent.getIntExtra(PAGE, 0)
                    for (i in 0 until 10) {
                        delay(1000)
                        log("JobService: page $page loading ${i * 10} %")
                    }
                    params?.completeWork(workItem) //завершение сервиса из очереди
                    workItem = params?.dequeueWork() // берём следующий элемент очереди
                }
            }
            jobFinished(params, true) //завершение сервиса вцелом
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
        Log.d("MyLog", "ScheduledJobService: $message")
        notifyLog(13, message)
    }

    companion object {
        const val JOB_ID = 113
        private const val PAGE = "page"

        fun newIntent(page: Int) = Intent().apply {
            putExtra(PAGE, page)
        }
    }
}