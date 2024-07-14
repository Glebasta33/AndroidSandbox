package com.github.gltrusov.background.services.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.github.gltrusov.background.databinding.ActivityServiceBinding

internal class ServiceActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityServiceBinding.inflate(layoutInflater)
    }

    private val logsReceiver = object : BroadcastReceiver() {
        private val logs = StringBuilder()
        override fun onReceive(context: Context, intent: Intent) {
            val log = intent.getStringExtra("log") ?: "empty"
            logs.append(log).append("\n")
            binding.logs.text = logs.toString()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        LocalBroadcastManager.getInstance(this).registerReceiver(logsReceiver, IntentFilter("BasicServiceLogs"))
        binding.blockingService.setOnClickListener {
            startService(BasicService.newIntent(this))
        }
        binding.notBlockingService.setOnClickListener {
            startService(BasicServiceWithBackground.newIntent(this))
        }
        binding.logs.movementMethod = ScrollingMovementMethod()
    }

    internal companion object {
        fun start(context: Context) =
            Intent(context, ServiceActivity::class.java).apply {
                context.startActivity(this)
            }
    }

}