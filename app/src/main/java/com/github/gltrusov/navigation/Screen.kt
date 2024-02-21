package com.github.gltrusov.navigation

sealed class Screen(
    val route: String
) {
    data object Root : Screen("root")

    data object ViewModel : Screen("view_model")
}