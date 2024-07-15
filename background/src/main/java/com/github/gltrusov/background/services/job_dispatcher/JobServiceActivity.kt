package com.github.gltrusov.background.services.job_dispatcher

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.gltrusov.background.databinding.ActivityJobServiceBinding

internal class JobServiceActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityJobServiceBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.jobService.setOnClickListener {
            val componentName = ComponentName(this, MyJobService::class.java)

            /**
             * JobInfo - объект с ограничениями для JobService
             */
            val jobInfo = JobInfo.Builder(MyJobService.JOB_ID, componentName)
                // устройство должно заряжаться:
                .setRequiresCharging(true)
                // необхоимо подключение к wifi
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                // перезапускать после выключения устройства
                .setPersisted(true)
                .build()

            val jobScheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
            jobScheduler.schedule(jobInfo)
        }

        binding.markdown.loadMarkdownFile("file:///android_asset/job_service.md")
    }

    internal companion object {
        fun start(context: Context) =
            Intent(context, JobServiceActivity::class.java).apply {
                context.startActivity(this)
            }
    }

}