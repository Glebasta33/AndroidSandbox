package com.github.gltrusov.multithreading.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.gltrusov.core.ui.compose.CodeLoggerScreen
import com.github.gltrusov.core.ui.compose.MarkdownFrom
import com.github.gltrusov.multithreading.presentation.navigation.MenuCell
import com.github.gltrusov.multithreading.sandbox.android.handler.HandlerAndExecutorsScreen
import com.github.gltrusov.multithreading.sandbox.fundamentals.RaceCondition
import com.github.gltrusov.multithreading.sandbox.fundamentals.ThreadsCreation
import com.github.gltrusov.multithreading.sandbox.fundamentals.ThreadsStoppage
import com.github.gltrusov.multithreading.sandbox.java.util.concurrent.atomic.Atomics
import com.github.gltrusov.multithreading.sandbox.java.util.concurrent.executors.ExecutorService
import com.github.gltrusov.multithreading.sandbox.java.util.concurrent.locks.ReentrantLock
import com.github.gltrusov.multithreading.sandbox.java.util.concurrent.locks.ReentrantReadWriteLock
import com.github.gltrusov.multithreading.sandbox.java.util.concurrent.synchronisers.CountDownLatch
import com.github.gltrusov.multithreading.sandbox.jmm.JmmSynchronized
import com.github.gltrusov.multithreading.sandbox.jmm.JmmSynchronizedReentrant
import com.github.gltrusov.multithreading.sandbox.jmm.Volatile
import java.util.concurrent.Executors

@Composable
internal fun RootNavGraph(
    screens: List<Screen>
) {
    val navController = rememberNavController()
    NavHost(
        navController,
        startDestination = "main",
        route = "root",
        modifier = Modifier.fillMaxSize()
    ) {
        mainGraph(navController, screens)
    }
}

internal fun NavGraphBuilder.mainGraph(navController: NavHostController, screens: List<Screen>) {
    composable("main") {
        LazyColumn {
            items(screens) { item ->
                MenuCell(item.javaClass.simpleName) {
                    navController.navigate(item.title)
                }
            }
        }
    }

    screens.forEach { screen ->
        composable(screen.title) {
            when (screen) {
                Screen.HandlerAndExecutors -> HandlerAndExecutorsScreen()
                Screen.ThreadsCreation -> CodeLoggerScreen(
                    markdown = { MarkdownFrom("threads_creation.md") },
                    action = { executorService.submit { ThreadsCreation() } }
                )
                Screen.ThreadsStoppage -> CodeLoggerScreen(
                    markdown = { MarkdownFrom("threads_stoppage.md") },
                    action = { executorService.submit { ThreadsStoppage() } }
                )
                Screen.RaceCondition -> CodeLoggerScreen(
                    markdown = { MarkdownFrom("race_condition.md") },
                    action = { executorService.submit { RaceCondition() } }
                )
                Screen.JmmSynchronized -> CodeLoggerScreen(
                    markdown = { MarkdownFrom("jmm_synchronized.md") },
                    action = { executorService.submit { JmmSynchronized() } }
                )
                Screen.JmmSynchronizedReentrant -> CodeLoggerScreen(
                    markdown = { MarkdownFrom("jmm_synchronized_reentrant.md") },
                    action = { executorService.submit { JmmSynchronizedReentrant() } }
                )
                Screen.Volatile -> CodeLoggerScreen(
                    markdown = { MarkdownFrom("volatile.md") },
                    action = { executorService.submit { Volatile() } }
                )
                Screen.ReentrantLock -> CodeLoggerScreen(
                    markdown = { MarkdownFrom("reentrant_lock.md") },
                    action = { executorService.submit { ReentrantLock() } }
                )
                Screen.ReentrantReadWriteLock -> CodeLoggerScreen(
                    markdown = { MarkdownFrom("reentrant_read_write_lock.md") },
                    action = { executorService.submit { ReentrantReadWriteLock() } }
                )
                Screen.ExecutorService -> CodeLoggerScreen(
                    markdown = { MarkdownFrom("ExecutorService.md") },
                    action = { executorService.submit { ExecutorService() } }
                )
                Screen.Atomics -> CodeLoggerScreen(
                    markdown = { MarkdownFrom("Atomics.md") },
                    action = { executorService.submit { Atomics() } }
                )
                Screen.CountDownLatch -> CodeLoggerScreen(
                    markdown = { MarkdownFrom("CountDownLatch.md") },
                    action = { executorService.submit { CountDownLatch() } }
                )
            }
        }
    }
}


private val executorService = Executors.newSingleThreadExecutor { runnable ->
    Thread(runnable, "Main")
}