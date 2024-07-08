package com.github.gltrusov.compose.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.ui.unit.sp

class ComposeSandboxActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Text("Hello Compose Module!!!", fontSize = 42.sp)
        }
    }

    internal companion object {
        fun createIntent(context: Context): Intent =
            Intent(context, ComposeSandboxActivity::class.java)
    }
}