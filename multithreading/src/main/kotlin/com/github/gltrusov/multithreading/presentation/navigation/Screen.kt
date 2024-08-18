package com.github.gltrusov.multithreading.presentation.navigation

internal sealed class Screen(
    val title: String
) {
    data object HandlerAndExecutors : Screen("Handler and Executors")
}