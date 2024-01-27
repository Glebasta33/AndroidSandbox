package com.github.gltrusov.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

/**
 * ViewModel может прнимать зависимости в конструктор.
 * Тк ViewModel создаётся системой, для инициализации ViewModel с параметрами в конструкторе нужно
 * использовать специальный механизм - интерфейс ViewModelProvider.Factory
 * (только реализации этого интерфейса могут создавать экземпляры ViewModel,
 * для создания экземпляра ViewModel и AndroidViewModel без параметров используется дефолтная фабрика).
 *
 * При реализации ViewModelProvider.Factory нужно переопределить метод create(Class<T>, CreationExtras)
 * CreationExtras позволяет получить доступ к релевантной информации, которая может помочь в инициализации ViewModel.
 *
 */
class ViewModelWithDependencies(
    private val exampleRepository: ExampleRepository
) : ViewModel() {

    // ViewModel logic ...
    fun doSomething() { Log.d("MyTest", "ViewModelWithDependencies: ${exampleRepository.getData()}") }

    companion object {
        val Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Получение контекста Application из extras
                val application = checkNotNull(extras[APPLICATION_KEY])

                // Имя доступ к Application, можно инджектить зависимости через DI.
                val repository = ExampleRepository(application)

                return ViewModelWithDependencies(repository) as T
            }
        }
    }

    /**
     * Фабрику можно создать используя Kotlin DSL:
     */
    val KotlinDslFactory = viewModelFactory {
        initializer {  // this: CreationExtras
            val repository = ExampleRepository(this[APPLICATION_KEY]!!)
            ViewModelWithDependencies(repository)
        }
    }
}

class ExampleRepository(private val application: Application) {
    fun getData() = application.packageName
}