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

    data object Coroutines : Screen("coroutines_graph") {
        data object Root : Screen("coroutines_root")
        data object CoroutinesInAndroid : Screen("coroutines_base")
    }

    data object ComposeCustomView : Screen("compose_custom_view") {
        data object Root : Screen("compose_root")
        data object CanvasTest : Screen("compose_canvas_test")
        data object PathAndBrush : Screen("compose_path_brush_test")
        data object DetectGestures : Screen("compose_detect_gestures")
        data object Terminal : Screen("compose_terminal")
    }
}