package com.github.gltrusov.background.services.foreground_service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RestartingService : Service() {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    /**
     * 1 метод ЖЦ. Вызывается при создании сервиса.
     */
    override fun onCreate() {
        super.onCreate()
        log("onCreate")
    }

    /**
     * 2 метод ЖЦ. Тут выполняется вся работа.
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        log("onStartCommand")
        //код сервиса выполняется в главном потоке, а значит блокирует его!
        coroutineScope.launch {
            for (i in 0 until 100) {
                delay(1000)
                log("Service is working $i in ${Thread.currentThread().name} thread")
            }
        }

        if (intent != null) {
            return when(intent.getStringExtra(START_SERVICE_CONSTANT)) {
                "START_NOT_STICKY" -> START_NOT_STICKY
                "START_STICKY" -> START_STICKY
                "START_REDELIVER_INTENT" -> START_REDELIVER_INTENT
                else -> super.onStartCommand(intent, flags, startId)
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    /**
     * 3 метод ЖЦ. Вызывается при уничтожении сервиса.
     */
    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
        log("onDestroy")
    }

    /**
     * Обязателен для переопределения!
     */
    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    private fun log(message: String) {
        Log.d("MyLog", "ServiceWithBackground: $message")
        val intent = Intent("BasicServiceLogs").apply {
            putExtra("log", "ServiceWithBackground: $message")
        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    companion object {
        private const val START_SERVICE_CONSTANT = "START_SERVICE_CONSTANT"
        fun newIntent(context: Context, constant: String): Intent {
            return Intent(context, RestartingService::class.java).apply {
                putExtra(START_SERVICE_CONSTANT, constant)
            }
        }
    }
}