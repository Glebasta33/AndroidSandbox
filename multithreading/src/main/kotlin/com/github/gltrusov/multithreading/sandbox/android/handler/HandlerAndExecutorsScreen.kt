package com.github.gltrusov.multithreading.sandbox.android.handler

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import kotlin.concurrent.thread

object Api {
    fun getName(): String {
        Thread.sleep(2000)
        return "Name"
    }

    fun getAvatarUrl(): String {
        Thread.sleep(1000)
        return "url"
    }
}

data class UserDetails(
    val name: String,
    val avatarUrl: String
)

@Composable
fun HandlerAndExecutorsScreen() {

    var state by remember { mutableStateOf<UserDetails?>(null) }

    val uiHandler by remember { mutableStateOf(Handler(Looper.getMainLooper())) }

    val executors by remember { mutableStateOf(Executors.newFixedThreadPool(3)) }

    LaunchedEffect(Unit) {
        thread {
            val nameFuture = executors.submit(Callable { Api.getName() })
            val avatarFuture = executors.submit(Callable { Api.getAvatarUrl() })

            val runtime = Runnable {
                val name = nameFuture.get()
                val avatar = avatarFuture.get()
                val userDetails = UserDetails(name, avatar)
                uiHandler.post { // Хотя стейт итак - Observable..
                    state = userDetails
                }
            }
            executors.submit(runtime)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = state?.toString() ?: "Loading", color = Color.White)
    }

}