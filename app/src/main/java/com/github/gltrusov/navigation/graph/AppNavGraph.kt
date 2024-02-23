package com.github.gltrusov.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.createGraph
import androidx.navigation.fragment.fragment
import com.github.gltrusov.RootCatalogFragment
import com.github.gltrusov.multithreading.coroutines.CoroutinesNavGraph
import com.github.gltrusov.navigation.Screen
import com.github.gltrusov.viewmodel.nav_samples.ViewModelsNavGraph

object AppNavGraph {
    fun setGraph(navController: NavController) {
        with(navController) {
            createGraph(
                startDestination = Screen.Root.route
            ) {
                RootCatalogNavGraph()
            }
                .also { navGraph ->
                    setGraph(navGraph, null)
                }
        }
    }
}

fun NavGraphBuilder.RootCatalogNavGraph() {
    fragment<RootCatalogFragment>(Screen.Root.route)
    ViewModelsNavGraph()
    CoroutinesNavGraph()
}