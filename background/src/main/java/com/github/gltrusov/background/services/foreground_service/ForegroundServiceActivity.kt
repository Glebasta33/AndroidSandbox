package com.github.gltrusov.background.services.foreground_service

import android.R
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.github.gltrusov.background.databinding.ActivityForegroundServiceBinding

internal class ForegroundServiceActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityForegroundServiceBinding.inflate(layoutInflater)
    }

    private val constants = listOf(
        "START_STICKY",
        "START_NOT_STICKY",
        "START_REDELIVER_INTENT"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.startServiceConstantSpinner.apply {
            val arrayAdapter = ArrayAdapter(
                this@ForegroundServiceActivity,
                R.layout.simple_spinner_item,
                constants
            )
            adapter = arrayAdapter
            arrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
            prompt = "Выбор константы для onStartCommand"
            setSelection(0)
        }

        binding.startServiceConstantSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    binding.serviceWithConstant.text =
                        "Запустить сервис с константой: ${constants[position]}"
                    binding.foregroundService.text =
                        "Запустить Foreground Service с константой: ${constants[position]}"
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}

            }

        binding.serviceWithConstant.text =
            "Запустить сервис с константой: ${binding.startServiceConstantSpinner.selectedItem as String}"
        binding.foregroundService.text =
            "Запустить Foreground Service с константой: ${binding.startServiceConstantSpinner.selectedItem as String}"

        binding.serviceWithConstant.setOnClickListener {
            startService(
                RestartingService.newIntent(
                    context = this@ForegroundServiceActivity,
                    constant = binding.startServiceConstantSpinner.selectedItem as String
                )
            )
        }

        binding.foregroundService.setOnClickListener {
            // startForegroundService обязывает в течение 5 секунд вызвать startForeground в сервисе, иначе приложение упадёт.
            ContextCompat.startForegroundService(
                this,
                ForegroundService.newIntent(
                    context = this@ForegroundServiceActivity,
                    constant = binding.startServiceConstantSpinner.selectedItem as String
                )
            )
        }

        binding.stopForegroundService.setOnClickListener {
            stopService(
                ForegroundService.newIntent(
                    context = this@ForegroundServiceActivity,
                    constant = ""
                )
            )
        }
    }

    internal companion object {
        fun start(context: Context) =
            Intent(context, ForegroundServiceActivity::class.java).apply {
                context.startActivity(this)
            }
    }
}