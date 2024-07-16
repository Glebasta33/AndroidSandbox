package com.github.gltrusov.background.services.work_manager

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.github.gltrusov.background.databinding.ActivityWorkManagerBinding
import com.github.gltrusov.background.notification.createLoggingNotificationChannel

class WorkManagerActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityWorkManagerBinding.inflate(layoutInflater)
    }

    private var page = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        createLoggingNotificationChannel()

        val workManager = WorkManager.getInstance(applicationContext)

        binding.workManager.setOnClickListener {
            workManager.enqueueUniqueWork(
                MyWorker.WORK_NAME,
                ExistingWorkPolicy.APPEND,
                MyWorker.makeRequest(page++)
            )
        }

        binding.markdown.loadMarkdownFile("file:///android_asset/work_manager.md")
    }

    internal companion object {
        fun start(context: Context) =
            Intent(context, WorkManagerActivity::class.java).apply {
                context.startActivity(this)
            }
    }

}