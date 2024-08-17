package com.github.gltrusov.multithreading.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import com.github.gltrusov.multithreading.presentation.navigation.RootNavGraph
import com.github.gltrusov.multithreading.presentation.navigation.Screen

class MultithreadingSandboxActivity : ComponentActivity() {
    private val screens = listOf<Screen>(
//        Screen.CanvasBasics,
//        Screen.PathAndBrush,
//        Screen.DetectGestures,
//        Screen.Terminal
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Text(text = "Hello Multithreading module on compose!")
            RootNavGraph(screens)
        }
    }

    internal companion object {
        fun createIntent(context: Context): Intent =
            Intent(context, MultithreadingSandboxActivity::class.java)
    }
}