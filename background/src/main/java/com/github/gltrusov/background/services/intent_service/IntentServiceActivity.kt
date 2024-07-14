package com.github.gltrusov.background.services.intent_service

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.gltrusov.background.databinding.ActivityIntentServiceBinding
import com.github.gltrusov.background.databinding.ActivityServiceBinding

internal class IntentServiceActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityIntentServiceBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.intentService.setOnClickListener {
            startService(MyIntentService.newIntent(this))
        }

        binding.markdown.loadMarkdownFile("file:///android_asset/intent_service.md")
    }

    internal companion object {
        fun start(context: Context) =
            Intent(context, IntentServiceActivity::class.java).apply {
                context.startActivity(this)
            }
    }

}