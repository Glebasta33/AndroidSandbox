package com.github.gltrusov.multithreading.coroutines

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.fragment.fragment
import androidx.navigation.navigation
import com.github.gltrusov.multithreading.coroutines.samples.CoroutinesInAndroidFragment
import com.github.gltrusov.navigation.Screen
import com.github.gltrusov.navigation.component.CoreFragment
import com.github.gltrusov.ui.theme.AndroidSandboxTheme

fun NavGraphBuilder.CoroutinesNavGraph() {
    navigation(startDestination = Screen.Coroutines.Root.route, route = Screen.Coroutines.route) {
        fragment<RootCoroutinesFragment>(Screen.Coroutines.Root.route)
        fragment<CoroutinesInAndroidFragment>(route = Screen.Coroutines.CoroutinesInAndroid.route)
    }
}

class RootCoroutinesFragment : CoreFragment() {

    private val screens = listOf(
        Screen.Coroutines.CoroutinesInAndroid,
    )


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(inflater.context).apply {
        setContent {
            AndroidSandboxTheme {
                Surface {
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
}