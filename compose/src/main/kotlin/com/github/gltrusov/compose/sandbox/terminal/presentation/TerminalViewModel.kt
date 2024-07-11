package com.github.gltrusov.compose.sandbox.terminal.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.gltrusov.compose.sandbox.terminal.data.ApiFactory
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TerminalViewModel : ViewModel() {

    private val apiService = ApiFactory.apiService

    private val _state = MutableStateFlow<TerminalScreenState>(TerminalScreenState.Initial)
    val state = _state.asStateFlow()

    private var lastState: TerminalScreenState = TerminalScreenState.Initial

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _state.value = lastState
    }

    init {
        loadBarList()
    }

    fun loadBarList(
        timeframe: Timeframe = Timeframe.HOUR_1
    ) {
        lastState = _state.value
        _state.value = TerminalScreenState.Loading
        viewModelScope.launch(exceptionHandler) {
            _state.value = TerminalScreenState.Content(
                apiService.loadBars(timeframe.path).bars,
                timeframe = timeframe
            )
        }
    }

}