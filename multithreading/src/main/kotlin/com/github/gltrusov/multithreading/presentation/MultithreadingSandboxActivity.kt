package com.github.gltrusov.multithreading.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class MultithreadingSandboxActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RootNavGraph(Screen.screens)
        }
    }

    internal companion object {
        fun createIntent(context: Context): Intent =
            Intent(context, MultithreadingSandboxActivity::class.java)
    }
}