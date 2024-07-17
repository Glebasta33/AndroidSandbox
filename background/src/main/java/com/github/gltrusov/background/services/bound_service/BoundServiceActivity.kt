package com.github.gltrusov.background.services.bound_service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.github.gltrusov.background.databinding.AcrivityBoundServiceBinding

internal class BoundServiceActivity : AppCompatActivity() {

    private val binding by lazy {
        AcrivityBoundServiceBinding.inflate(layoutInflater)
    }

    /**
     * ServiceConnection реализует подписку на сервис
     */
    private val serviceConnection = object : ServiceConnection {
        /**
         * onServiceConnected вызывается при подписке на сервис.
         * В service: IBinder прилетит экземпляр Binder, который
         * мы возвращаем из onBind сервиса.
         */
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = (service as? BoundService.LocalBinder) ?: return
            val boundService = binder.getService()
            boundService.onProgressChanged = { progress ->
                binding.progressBar.progress = progress
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d("MyLog", "ServiceConnection: onServiceDisconnected")
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.boundService.setOnClickListener {
            startService(BoundService.newIntent(this))
        }
        binding.markdown.loadMarkdownFile("file:///android_asset/bound_service.md")
    }

    internal companion object {
        fun start(context: Context): Intent =
            Intent(context, BoundServiceActivity::class.java).apply {
                context.startActivity(this)
            }
    }

    /**
     * Подписка на сервис осуществляется методом bindService,
     * в который нужно передать экземпляр ServiceConnection
     */
    override fun onStart() {
        super.onStart()
        bindService(
            BoundService.newIntent(this),
            serviceConnection,
            0
        )
    }

    override fun onStop() {
        super.onStop()
        unbindService(serviceConnection)
    }
}