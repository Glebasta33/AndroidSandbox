package com.github.gltrusov.background.services.job_dispatcher

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.app.job.JobWorkItem
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
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

        binding.buttonSavePages.setOnClickListener {
            binding.jobServiceParams.text =
                "Start JobService with param: ${binding.editTextPagesInput.text}"
            binding.scheduledJobService.text =
                "Start ScheduledJobService with param: ${binding.editTextPagesInput.text}"
            binding.editTextPagesInput.clearFocus()
        }

        binding.jobServiceParams.setOnClickListener {
            val componentName = ComponentName(this, JobServiceWithParam::class.java)

            val jobInfo = JobInfo.Builder(JobServiceWithParam.JOB_ID, componentName)
                // передача параметров
                .setExtras(
                    JobServiceWithParam.newBundle(
                        binding.editTextPagesInput.text.toString().toInt()
                    )
                )
                .setRequiresCharging(true)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setPersisted(true)
                .build()

            val jobScheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
            jobScheduler.schedule(jobInfo)
        }

        binding.scheduledJobService.setOnClickListener {
            val componentName = ComponentName(this, ScheduledJobService::class.java)

            val jobInfo = JobInfo.Builder(ScheduledJobService.JOB_ID, componentName)
                .setRequiresCharging(true)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
//                .setPersisted(true) - нельзя использовать для очереди сервисов
                .build()

            val jobScheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
            val page = binding.editTextPagesInput.text.toString().toInt()

            /**
             * С Android 8 (API 26) можно выполнять JobService последовательно
             * друг за другом через метод enqueue.
             *
             * При API 26 аналог - IntentService.
             *
             * JobIntent делает эту проверку под капотом.
             */
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val intent = ScheduledJobService.newIntent(page)
                jobScheduler.enqueue(jobInfo, JobWorkItem(intent))
            } else {
                startService(MyIntentService2.newIntent(this, page))
            }
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