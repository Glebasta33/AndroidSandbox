package com.github.gltrusov.compose.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.gltrusov.compose.sandbox.CanvasBasics
import com.github.gltrusov.compose.sandbox.DetectGestures
import com.github.gltrusov.compose.sandbox.PathAndBrush
import com.github.gltrusov.compose.sandbox.terminal.presentation.Terminal

@Composable
internal fun RootNavGraph(
    screens: List<Screen>
) {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "main", route = "root", modifier = Modifier.fillMaxSize()) {
        mainGraph(navController, screens)
    }
}

internal fun NavGraphBuilder.mainGraph(navController: NavHostController, screens: List<Screen>) {
    composable("main") {
        LazyColumn {
            items(screens) { item ->
                MenuCell(item.javaClass.simpleName) {
                    navController.navigate(item.route)
                }
            }
        }
    }

    screens.forEach { screen ->
        composable(screen.route) {
            when(screen) {
                Screen.CanvasBasics -> CanvasBasics()
                Screen.PathAndBrush -> PathAndBrush()
                Screen.DetectGestures -> DetectGestures()
                Screen.Terminal -> Terminal()
            }
        }
    }
}