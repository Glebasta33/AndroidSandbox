package com.github.gltrusov.compose

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
import com.github.gltrusov.compose.samples.ComposeCanvasTestFragment
import com.github.gltrusov.compose.samples.DetectGesturesFragment
import com.github.gltrusov.compose.samples.PathAndBrushFragment
import com.github.gltrusov.compose.samples.terminal.presentation.ComposeTerminalFragment
import com.github.gltrusov.navigation.Screen
import com.github.gltrusov.navigation.component.CoreFragment
import com.github.gltrusov.ui.theme.AndroidSandboxTheme

fun NavGraphBuilder.ComposeCustomViewNavGraph() {
    navigation(
        startDestination = Screen.ComposeCustomView.Root.route,
        route = Screen.ComposeCustomView.route
    ) {
        fragment<RootComposeFragment>(Screen.ComposeCustomView.Root.route)
        fragment<ComposeCanvasTestFragment>(Screen.ComposeCustomView.CanvasTest.route)
        fragment<PathAndBrushFragment>(Screen.ComposeCustomView.PathAndBrush.route)
        fragment<DetectGesturesFragment>(Screen.ComposeCustomView.DetectGestures.route)
        fragment<ComposeTerminalFragment>(Screen.ComposeCustomView.Terminal.route)
    }
}

class RootComposeFragment : CoreFragment() {

    private val screens = listOf(
        Screen.ComposeCustomView.CanvasTest,
        Screen.ComposeCustomView.PathAndBrush,
        Screen.ComposeCustomView.DetectGestures,
        Screen.ComposeCustomView.Terminal
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