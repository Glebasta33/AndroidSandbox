package com.github.gltrusov.compose.samples.terminal.presentation

import com.github.gltrusov.compose.samples.terminal.data.Bar

sealed class TerminalScreenState {

    data object Initial : TerminalScreenState()

    data object Loading : TerminalScreenState()

    data class Content(
        val bars: List<Bar>,
        val timeframe: Timeframe
    ) : TerminalScreenState()

}