package com.github.gltrusov.compose.presentation.navigation

internal sealed class Screen(
    val route: String
) {
    data object CanvasBasics : Screen("canvas_basics")
    data object PathAndBrush : Screen("compose_path_brush_test")
    data object DetectGestures : Screen("compose_detect_gestures")
    data object Terminal : Screen("compose_terminal")
}