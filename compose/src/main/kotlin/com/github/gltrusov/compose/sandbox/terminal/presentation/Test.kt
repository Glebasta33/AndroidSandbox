package com.github.gltrusov.compose.sandbox.terminal.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun Test() {
    var number by rememberSaveable(
        stateSaver = TestData.Saver
    ) {
        mutableStateOf(TestData(0))
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable { number++ },
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Text: $number")
    }
}

data class TestData(val number: Int) {

    operator fun inc(): TestData = copy(number = number + 1)

    companion object {
        val Saver: Saver<TestData, Int> = Saver(
            save = { currentState ->
                currentState.number
            },
            restore = { savedValue: Int ->
                TestData(savedValue)
            }
        )
    }
}