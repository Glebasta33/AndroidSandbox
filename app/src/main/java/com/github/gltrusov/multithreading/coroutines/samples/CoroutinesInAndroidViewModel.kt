package com.github.gltrusov.multithreading.coroutines.samples

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlin.random.Random

/**
 * viewModelScope - закрывается в onCleared.
  */
class CoroutinesInAndroidViewModel : ViewModel() {

    val flow = flow<Int> {
        repeat(1000) {
            delay(5000)
            emit(it)
        }
    }

    private val _state = MutableStateFlow(0)
    val state: StateFlow<Int> = _state.asStateFlow()

    fun loadData() {
        viewModelScope.launch {
            val data = getDataFromRemote()
            _state.value = data
        }
    }

}

private suspend fun getDataFromRemote(): Int {
    delay(2000)
    return Random.nextInt(200, 500)
}