package com.github.gltrusov.multithreading.presentation

internal sealed class Screen(
    val title: String
) {

    data object ThreadsCreation : Screen("Создание потоков")
    data object ThreadsStoppage : Screen("Приостановка потоков")
    data object RaceCondition : Screen("Состояние гонки")
    data object JmmSynchronized : Screen("Java Memory Model и Synchronized")
    data object JmmSynchronizedReentrant : Screen("Synchronized и Reentrant")
    data object Volatile : Screen("Volatile")
    data object ReentrantLock : Screen("ReentrantLock")
    data object ReentrantReadWriteLock : Screen("ReentrantReadWriteLock")
    data object ExecutorService : Screen("ExecutorService")
    data object Atomics : Screen("Atomics")
    data object CountDownLatch : Screen("CountDownLatch")


    data object HandlerAndExecutors : Screen("Handler and Executors")

    companion object {
        val screens = listOf(
            ThreadsCreation,
            ThreadsStoppage,
            RaceCondition,
            JmmSynchronized,
            JmmSynchronizedReentrant,
            Volatile,
            ReentrantLock,
            ReentrantReadWriteLock,
            ExecutorService,
            Atomics,
            CountDownLatch,

            HandlerAndExecutors
        )
    }
}