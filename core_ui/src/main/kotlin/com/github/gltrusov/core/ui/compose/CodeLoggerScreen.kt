package com.github.gltrusov.core.ui.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@Composable
fun CodeLoggerScreen(
    markdown: @Composable ColumnScope.() -> Unit,
    action: () -> Unit
) {

    val logsState by UiLogger.logs.collectAsState()

    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()

    LaunchedEffect(lifecycleState) {
        if (lifecycleState != Lifecycle.State.RESUMED) {
             UiLogger.clearLogs()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        markdown()

        Icon(
            Icons.Filled.PlayArrow,
            contentDescription = null,
            tint = Color.Green,
            modifier = Modifier
                .size(18.dp)
                .padding(2.dp)
                .clickable { action.invoke() }
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 4.dp)
                .fillMaxWidth()
        ) {
            logsState.forEach { line ->
                Text(line, color = Color.Green)
            }
        }
    }
}

internal object UiLogger {

    private val _logs = MutableStateFlow(emptyList<String>())
    val logs: StateFlow<List<String>> = _logs.asStateFlow()

    fun log(line: String) {
        _logs.update { prev ->
            prev.toMutableList().apply { add(line) }
        }
    }

    fun clearLogs() {
        _logs.update { emptyList() }
    }
}

fun logOnUi(text: String) {
        println(text)
        UiLogger.log(text) //TODO: Добавить отображение времени HH:mm:SS
}