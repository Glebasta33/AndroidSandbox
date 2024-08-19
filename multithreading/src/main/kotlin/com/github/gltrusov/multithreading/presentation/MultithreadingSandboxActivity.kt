package com.github.gltrusov.multithreading.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class MultithreadingSandboxActivity : ComponentActivity() {
    private val screens = listOf(
        Screen.HandlerAndExecutors,
        Screen.ThreadsCreation,
        Screen.ThreadsStoppage,
        Screen.RaceCondition
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RootNavGraph(screens)
        }
    }

    internal companion object {
        fun createIntent(context: Context): Intent =
            Intent(context, MultithreadingSandboxActivity::class.java)
    }
}