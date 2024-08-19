package com.github.gltrusov.multithreading.sandbox.fundamentals

import com.github.gltrusov.core.ui.compose.logOnUi
import com.github.gradle_sandbox.Markdown
import kotlin.concurrent.thread

/**
 * Можно быстро создать и запустить поток
 * с помощью функции thread в Kotlin:
 * ```
 * public fun thread(
 *     start: Boolean = true,
 *     isDaemon: Boolean = false,
 *     contextClassLoader: ClassLoader? = null,
 *     name: String? = null,
 *     priority: Int = -1,
 *     block: () -> Unit
 * ): Thread {
 *     val thread = object : Thread() {
 *         public override fun run() {
 *             block()
 *         }
 *     }
 *     if (isDaemon)
 *         thread.isDaemon = true
 *     if (priority > 0)
 *         thread.priority = priority
 *     if (name != null)
 *         thread.name = name
 *     if (contextClassLoader != null)
 *         thread.contextClassLoader = contextClassLoader
 *     if (start)
 *         thread.start() //Стартует автоматически!
 *     return thread
 * }
 * ```
 */

@Markdown("threads_stoppage.md")
fun ThreadsStoppage() {
    val thread = thread {
        repeat(100) { i ->
            // СПОСОБ 1:
            try {
                // Выполнение тяжёлой работы
                Thread.sleep(1000) // throws InterruptedException
                logOnUi("Hello from ${Thread.currentThread().name} $i!")
            } catch (e: InterruptedException) {
                // Очистить ресурсы
            }

            // СПОСОБ 2:
            if (/*!Thread.currentThread().isInterrupted*/ false) {
                // Выполнение тяжёлой работы
                logOnUi("Hello from ${Thread.currentThread().name} $i!")
            } else {
                // Очистить ресурсы
            }
        }
    }
    Thread.sleep(3000)

    // Поток должен быть отменён в подходящий момент (Принцип: Cancellation is cooperative).
    // interrupt() - лишь устанавливает isInterrupted в true (бросает InterruptedException),
    // а обработка этого осуществляется внутри кода потока.
    thread.interrupt()
    //    thread.stop() - deprecated (unsafe)

    thread.join()
}

fun main() {
    ThreadsStoppage()
}