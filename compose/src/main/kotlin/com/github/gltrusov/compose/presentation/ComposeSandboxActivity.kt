package com.github.gltrusov.compose.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.github.gltrusov.compose.presentation.navigation.RootNavGraph
import com.github.gltrusov.compose.presentation.navigation.Screen

class ComposeSandboxActivity : ComponentActivity() {
    private val screens = listOf(
        Screen.CanvasBasics,
        Screen.PathAndBrush,
        Screen.DetectGestures,
        Screen.Terminal
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RootNavGraph(screens)
        }
    }

    internal companion object {
        fun createIntent(context: Context): Intent =
            Intent(context, ComposeSandboxActivity::class.java)
    }
}