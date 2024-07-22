package com.github.gltrusov.rxjava.presentation.custom_observable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.github.gltrusov.rxjava.presentation.MarkdownFrom

@Composable
fun CustomObservableScreen(
    state: String
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = state)
        MarkdownFrom(fileName = "custom_observable.md", modifier = Modifier.weight(1f))
    }
}