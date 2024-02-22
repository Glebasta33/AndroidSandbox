package com.github.gltrusov.viewmodel.nav_samples

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.fragment.fragment
import androidx.navigation.navigation
import com.github.gltrusov.navigation.Screen
import com.github.gltrusov.navigation.component.CoreFragment
import com.github.gltrusov.ui.theme.AndroidSandboxTheme

fun NavGraphBuilder.ViewModelsNavGraph() {
    navigation(startDestination = Screen.ViewModel.Root.route, route = Screen.ViewModel.route) {
        fragment<RootViewModelsFragment>(Screen.ViewModel.Root.route)
        fragment<ViewModel1Fragment>(route = Screen.ViewModel.ViewModel1.route)
        fragment<ViewModel2Fragment>(route = Screen.ViewModel.ViewModel2.route)
    }
}

class RootViewModelsFragment : CoreFragment() {

    val screens = listOf(
        Screen.ViewModel.ViewModel1,
        Screen.ViewModel.ViewModel2
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(inflater.context).apply {
        setContent {
            AndroidSandboxTheme {
                LazyColumn {
                    items(screens) { item ->
                        Text(
                            text = item.javaClass.simpleName,
                            modifier = Modifier
                                .padding(16.dp)
                                .clickable {
                                    navController.navigate(item.route)
                                }
                        )
                    }
                }
            }
        }
    }
}