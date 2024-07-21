package com.github.gltrusov.rxjava.presentation.network_call

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.github.gltrusov.rxjava.data.TaskDto
import com.github.gltrusov.rxjava.presentation.MarkdownFrom

internal sealed interface TasksScreenState {

    data object Loading : TasksScreenState

    data class Loaded(val tasks: List<TaskDto>) : TasksScreenState

    data class Error(val message: String) : TasksScreenState

}

@RequiresApi(Build.VERSION_CODES.S)
@Composable
internal fun TasksScreen(
    state: TasksScreenState
) {
    val context = LocalContext.current

    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
    ) {
        Column {
            MarkdownFrom(fileName = "rxjava_network.md", modifier = Modifier.weight(1f))
            Box(modifier = Modifier
                .weight(1f)
                .fillMaxWidth(), contentAlignment = Alignment.Center) {
                when (state) {
                    TasksScreenState.Loading -> CircularProgressIndicator()
                    is TasksScreenState.Error -> Text(state.message)
                    is TasksScreenState.Loaded -> LoadedContent(state.tasks)
                }
            }
        }

    }
}

@Composable
private fun LoadedContent(
    tasks: List<TaskDto>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        items(tasks) { task ->
            TaskCell(task)
        }
    }
}

@Composable
private fun TaskCell(taskDto: TaskDto) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row {
            Checkbox(checked = taskDto.completed, onCheckedChange = {})
            Text(taskDto.title)
        }
    }
}