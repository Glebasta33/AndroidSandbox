package com.github.gltrusov.multithreading.presentation

internal sealed class Screen(
    val title: String
) {

    data object ThreadsCreation : Screen("Создание потоков")
    data object ThreadsStoppage : Screen("Приостановка потоков")
    data object RaceCondition : Screen("Состояние гонки")
    data object HandlerAndExecutors : Screen("Handler and Executors")

    companion object {
        val screens = listOf(
            ThreadsCreation,
            ThreadsStoppage,
            RaceCondition,


            HandlerAndExecutors
        )
    }
}