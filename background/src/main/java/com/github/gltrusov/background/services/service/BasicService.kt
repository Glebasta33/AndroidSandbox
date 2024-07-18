package com.github.gltrusov.background.services.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.github.gradle_sandbox.Markdown

@Markdown("basic_service.md")
class BasicService : Service() {

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
        for (i in 0 until 100) {
            Thread.sleep(1000)
            log("Service is working $i in ${Thread.currentThread().name} thread")
        }
        return super.onStartCommand(intent, flags, startId)
    }

    /**
     * 3 метод ЖЦ. Вызывается при уничтожении сервиса.
     */
    override fun onDestroy() {
        super.onDestroy()
        log("onDestroy")
    }

    /**
     * Обязателен для переопределения!
     */
    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    private fun log(message: String) {
        Log.d("MyLog", "BasicService: $message")
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, BasicService::class.java)
        }
    }
}