package com.github.gltrusov.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * ViewModel нужен, чтобы удерживать состояние экрана и связанную бизнес логику при изменениях конфигурации.
 * ViewModel кеширует состояния поближе к UI, следовательно нет необходимости извлекать его откуда то при навигации или повороте экрана.
 *
 * Класс ViewModel - абстрактный класс, с одним методом onCleared(), который срабатывает при уничтожении ViewModel.
 * Согласно MVVM, данные внутри ViewModel должны быть изолированы от View - нужно использовать паттерн "Observer": LiveData, StateFlow.
 *
 * Для получения ссылки на экземпляр ViewModel - используется ViewModelProvider.
 * ...
 *
 * ЖЦ VM привязан к ЖЦ ViewModelStoreOwner. ViewModelScope живёт, пока не будет вызван метод onCleared.
 * VM создаётся в onCreate в первый раз, затем при изменении конфигурации (повороте экрана), VM берётся из ViewModelStore.
 * onCleared вызывается в момент уничтожения ViewModelStoreOwner (Activity - onDestroy, Fragment - onDettach).
 *
 * Почему onCleared не вызывается при повороте экрана, хотя onDestroy в Activity вызывается?
 * При повороте экрана вызывается метод ComponentActivity onRetainNonConfigurationInstance(), который
 * сохраняет viewModelStore (метод clear() у ViewModelStore не вызывается).
 */
class ExampleSimpleViewModel : ViewModel() {

    private val _state: MutableLiveData<Int> = MutableLiveData(0)
    val state: LiveData<Int> = _state

    fun incrementState() {
        _state.postValue(_state.value?.plus(1))
    }

    init {
        Log.d("MyTest", "ExampleViewModel.init")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("MyTest", "ExampleViewModel.onCleared")
    }
}