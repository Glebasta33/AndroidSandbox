package com.github.gltrusov.background.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.gltrusov.background.databinding.ActivityBackgroundBinding
import com.github.gltrusov.background.notification.NotificationAboutActivity
import com.github.gltrusov.background.services.foreground_service.ForegroundServiceActivity
import com.github.gltrusov.background.services.intent_service.IntentServiceActivity
import com.github.gltrusov.background.services.service.ServiceActivity

internal class BackgroundActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityBackgroundBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.simpleService.setOnClickListener {
            ServiceActivity.start(this)
        }
        binding.aboutNotification.setOnClickListener {
            NotificationAboutActivity.start(this)
        }
        binding.foregroundService.setOnClickListener {
            ForegroundServiceActivity.start(this)
        }
        binding.intentService.setOnClickListener {
            IntentServiceActivity.start(this)
        }
    }

    internal companion object {
        fun createIntent(context: Context): Intent =
            Intent(context, BackgroundActivity::class.java)
    }

}