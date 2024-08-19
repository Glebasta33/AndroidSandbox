package com.github.gltrusov.multithreading.presentation

internal sealed class Screen(
    val title: String
) {
    data object HandlerAndExecutors : Screen("Handler and Executors")
    data object ThreadsCreation : Screen("Создание потоков")
    data object ThreadsStoppage : Screen("Приостановка потоков")
    data object RaceCondition : Screen("Состояние гонки")
}