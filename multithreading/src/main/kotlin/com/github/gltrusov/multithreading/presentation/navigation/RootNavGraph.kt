package com.github.gltrusov.multithreading.presentation.navigation

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
import com.github.gltrusov.multithreading.sandbox.android.handler.HandlerAndExecutorsScreen

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
                    navController.navigate(item.title)
                }
            }
        }
    }

    screens.forEach { screen ->
        composable(screen.title) {
            when(screen) {
                Screen.HandlerAndExecutors -> HandlerAndExecutorsScreen()
            }
        }
    }
}