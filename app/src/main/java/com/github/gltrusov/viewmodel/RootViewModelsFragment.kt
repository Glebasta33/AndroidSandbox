package com.github.gltrusov.viewmodel

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
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.Navigation
import androidx.navigation.fragment.fragment
import androidx.navigation.navigation
import com.github.gltrusov.R
import com.github.gltrusov.navigation.Screen
import com.github.gltrusov.ui.theme.AndroidSandboxTheme
import com.github.gltrusov.viewmodel.nav_samples.ViewModel1Fragment
import com.github.gltrusov.viewmodel.nav_samples.ViewModel2Fragment

fun NavGraphBuilder.ViewModelsNavGraph() {
    navigation(startDestination = Screen.ViewModel.Root.route, route = Screen.ViewModel.route) {
        fragment<RootViewModelsFragment>(Screen.ViewModel.Root.route)
        fragment<ViewModel1Fragment>(route = Screen.ViewModel.ViewModel1.route)
        fragment<ViewModel2Fragment>(route = Screen.ViewModel.ViewModel2.route)
    }
}

class RootViewModelsFragment : Fragment() {

    val screens = listOf(
        Screen.ViewModel.ViewModel1,
        Screen.ViewModel.ViewModel2
    )

    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
    }

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