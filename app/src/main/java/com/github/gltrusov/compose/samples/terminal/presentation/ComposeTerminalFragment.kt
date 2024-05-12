package com.github.gltrusov.compose.samples.terminal.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.gltrusov.navigation.component.CoreFragment

class ComposeTerminalFragment : CoreFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(inflater.context).apply {
        setContent {
            val viewModel: TerminalViewModel = viewModel()
            val screenState = viewModel.state.collectAsState()
            when(val currentState = screenState.value) {
                is TerminalScreenState.Content -> {
                    Log.d("MyTest", currentState.bars.toString())
                }
                is TerminalScreenState.Initial -> {
                    Log.d("MyTest", "Initial")
                }
            }
        }
    }

}