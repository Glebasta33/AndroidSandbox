package com.github.gltrusov.navigation

sealed class Screen(
    val route: String
) {
    data object Root : Screen("root")

    data object ViewModel : Screen("view_model_graph") {
        data object Root : Screen("view_model_root")
        data object ViewModel1 : Screen("view_model1")
        data object ViewModel2 : Screen("view_model2")
    }

}