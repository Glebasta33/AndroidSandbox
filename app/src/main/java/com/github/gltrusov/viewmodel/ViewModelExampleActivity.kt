package com.github.gltrusov.viewmodel

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.github.gltrusov.dagger.c2_dependency_organization.appComponent
import com.github.gltrusov.viewmodel.di.DIViewModel
import com.github.gltrusov.viewmodel.di.DIViewModel2
import com.github.gltrusov.viewmodel.di.DIViewModelFactory
import javax.inject.Inject
import kotlin.concurrent.thread

/**
 * [Основной источник: ViewModel docs](https://developer.android.com/topic/libraries/architecture/viewmodel)
 */
class ViewModelExampleActivity : ComponentActivity() {

    /**
     * Для иньекции ViewModel достаточно заинжектить фабрику и передать её в ViewModelProvider.
     */
    @Inject
    lateinit var diViewModelFactory: DIViewModelFactory
    private val diViewModel: DIViewModel by viewModels { diViewModelFactory }
    private val diViewModel2: DIViewModel2 by viewModels { diViewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)
        super.onCreate(savedInstanceState)

        diViewModel.loadData()
        diViewModel2.loadData()

        /**
         * ViewModelProvider первым параметром принимает ViewModelStoreOwner.
         * ViewModelStoreOwner интерфейс (реализуется Activity, Fragment...) с одним полем ViewModelStore.
         * ViewModelStore хранить в себе мапу (... mutableMapOf<String, ViewModel>()), а также методы put, get и clear.
         * Метод clear вызывает onCleared у всех VM в данном сторе.
         *
         * При инициализации VM закрепляется за объектом, реализующим интерфейс ViewModelStoreOwner.
         * ЖЦ VM становится связана с ЖЦ ViewModelStoreOwner (Activity, Fragment...).
         *
         * Также ViewModelProvider предоставляет возможность передать свою реализацию ViewModelProvider.Factory.
         * (По умолчанию используется DefaultFactory).
         * У ViewModelProvider несколько конструкторов. В данном случае используется конструктор с 1 параметром
         * (используется дефолтная фабрика). Затем у экземпляра ViewModelProvider вызывается метод get, который принимает Class<T> нашей VM.
         * Затем метод get: сначала пробует достать VM из ViewModelStore по ключу (название класса VM),
         * еслни не находит: создаёт с помощью factory новую VM, возвращает её, а также кладёт в ViewModelStore.
         *
         */
        val viewModel: ExampleSimpleViewModel = ViewModelProvider(this)[ExampleSimpleViewModel::class.java]
        /**
         * by viewModels (делает то же самое) - extension-функция, использующая делегат для ленивой инициализации VM.
         * Последний параметр factoryProducer: (() -> Factory)? = null позволяет передать свою фабрику.
         */
//       val viewModel: ExampleSimpleViewModel by viewModels()

        viewModel.state.observe(this) { state ->
            Toast.makeText(this, "State: $state", Toast.LENGTH_SHORT).show()
        }

        thread {
            repeat(5) {
                Thread.sleep(300)
                viewModel.incrementState()
            }
        }

        viewModelWithDependencies.doSomething()
    }

    /**
     * Кастомную фабрику можно передать в ViewModelProvider.
     * Последний параметр factoryProducer: (() -> Factory)? = null позволяет передать свою фабрику.
     */
    private val viewModelWithDependencies: ViewModelWithDependencies by viewModels { ViewModelWithDependencies.Factory }
}