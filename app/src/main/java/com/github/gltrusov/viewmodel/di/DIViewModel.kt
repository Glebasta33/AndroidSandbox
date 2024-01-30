package com.github.gltrusov.viewmodel.di

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.gltrusov.dagger.c2_dependency_organization.NewsRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class DIViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    fun loadData() {
        viewModelScope.launch {
            Log.d("MyTest", "DIViewModel: " + newsRepository.getNews("id").toString())
        }
    }

}