package com.github.gltrusov.viewmodel.di

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.gltrusov.dagger.androidbroadcast.c2_dependency_organization.Analytics
import kotlinx.coroutines.launch
import javax.inject.Inject

class DIViewModel2 @Inject constructor(
    private val analytics: Analytics
) : ViewModel() {

    fun loadData() {
        viewModelScope.launch {
            Log.d("MyTest", "DIViewModel2: " + analytics.javaClass.simpleName)
        }
    }

}