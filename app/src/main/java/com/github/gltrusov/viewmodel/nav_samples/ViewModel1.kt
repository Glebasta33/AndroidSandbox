package com.github.gltrusov.viewmodel.nav_samples

import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel

class ViewModel1 : ViewModel() {

    val state = mutableIntStateOf(0)

    fun increment() {
        state.intValue++
    }

}