package com.github.gltrusov.multithreading.sandbox.java.util.concurrent.executors

import com.github.gltrusov.core.ui.compose.logOnUi
import com.github.gradle_sandbox.Markdown
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

/**
 * Executor:
 * public interface Executor {
 *      void execute(Runnable command);
 * }
 *
 * ExecutorService:
 * public interface ExecutorService extends Executor {
 *     <T> Future<T> submit(Callable<T> task); // Future - аналог Deferred из корутин.
 *     <T> Future<T> submit(Runnable task, T result);
 *     Future<?> submit(Runnable task);
 *     void shutdown();
 * }
 * Executors - позволяет создать пул потоков для исполнения задач.
 */
@Markdown("ExecutorService.md")
fun ExecutorService() {
    val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    val future: Future<String> = executorService.submit(
        Callable {
            Thread.sleep(2000)
            "Asynchronous task"
        }
    )

    val result = future.get()
    logOnUi("Result: $result")
}
fun main() {
    ExecutorService()
}