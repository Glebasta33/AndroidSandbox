package com.github.gltrusov.background.services.job_dispatcher

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log
import com.github.gltrusov.background.notification.createLoggingNotificationChannel
import com.github.gltrusov.background.notification.notifyLog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * JobService может выполняться без показа уведомления.
 * На JobService накладываются ограничения, при каких условиях сервис
 * будет выполняться (например, при подключении к WiFi, или при зарядке).
 *
 * Для работы JobService нужно добавить резрешение
 * в тег сервиса в манифесте:
 * <service android:name=".services.job_dispatcher.MyJobService"
 *   android:permission="android.permission.BIND_JOB_SERVICE"/>
 */
class MyJobService : JobService() {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()
        createLoggingNotificationChannel()
        log("onCreate")
    }

    /**
     * Тут выполняется вся работа сервиса.
     * Код onStartJob выполняется в главном потоке.
     * onStartJob: Boolean говорит о том, выполняется ли ещё работа.
     * - true - нужно возращать при выполнении асинхронной работы (как правило).
     * - false - сервис завершил работу (синхронно как правило).
     *
     * jobFinished - для ручного завершения сервиса (при асинхронной работе).
     * Второй boolean-параметр - нужно ли перезапускать сервис.
     */
    override fun onStartJob(params: JobParameters?): Boolean {
        log("onStartJob")
        coroutineScope.launch {
            for (page in 0 until 10) {
                for (i in 0 until 10) {
                    delay(1000)
                    log("JobService: page $page loading ${i * 10} %")
                }
            }
            jobFinished(params, true)
        }
        return true
    }

    /**
     * onStopJob - вызывается, когда система убивает сервис,
     * но не когда вручную вызывается jobFinished.
     * onStopJob: Boolean - нужно ли перезапустить сервис
     * после того, как его убила система.
     */
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
        const val JOB_ID = 111
    }
}