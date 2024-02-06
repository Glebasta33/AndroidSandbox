package com.github.gltrusov.dagger.c4_multibinding.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.gltrusov.dagger.c3_subcomponents.component.AppScope
import javax.inject.Inject
import dagger.Binds
import dagger.Component
import dagger.MapKey
import dagger.Module
import dagger.Subcomponent
import javax.inject.Provider
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import javax.inject.Scope
import kotlin.reflect.KClass

class MainViewModel @Inject constructor() : ViewModel()

class DetailsViewModel @Inject constructor() : ViewModel()

class SecondViewModel @Inject constructor() : ViewModel()


@Component(modules = [AppBindsModule::class])
@AppScope
interface AppComponent {

    val factory: MultiViewModelFactory

    val secondComponent: SecondComponent.Builder

    @Component.Builder
    interface Builder {

        fun build(): AppComponent
    }
}

@Module(
    subcomponents = [SecondComponent::class] //<- для примера с multibinding в subcomponent
)
interface AppBindsModule {

    /**
     * ClassKey - один из способов указания ключа для мапы, наряду с StringKey, LongKey и IntKey.
     * А также можно создавать собственные аннотации для указания ключа (ViewModelKey).
     */
    @Binds
//    @[IntoMap ClassKey(MainViewModel::class)]
    @[IntoMap ViewModelKey(MainViewModel::class)] //<- [ ] - фишка Котлина для объявления нескольких аннотаций в массиве
    fun provideMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @[IntoMap ViewModelKey(DetailsViewModel::class)]
    fun provideDetailsViewModel(detailsViewModel: DetailsViewModel): ViewModel

}

/**
 * Аннотация аналогична ClassKey, но типы классов, которые могут быть ключами, строго ограничены классом ViewModel.
 */
@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)

/**
 * Provider<ViewModel> - тут Provider играет важную роль. Без javax.inject.Provider, все ViewModel в мапе создадуться сразу и будут находиться в графе.
 * Provider обеспечивает создание экземпляра только при необходимосоти - при создании фабрикой.
 */
class MultiViewModelFactory @Inject constructor(
    private val viewModelFactories: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModelFactories.getValue(modelClass as Class<ViewModel>).get() as T
    }

    val viewModelsClasses get() = viewModelFactories.keys
}

fun main() {
//    test1()
    test2()
}

private fun test1() {
    val appComponent = DaggerAppComponent.builder().build()
    val viewModel = appComponent.factory.create(MainViewModel::class.java)
    print(viewModel) // com.github.gltrusov.dagger.c4_multibinding.map.MainViewModel@68837a77
}


/**
 * Важной возможностью Multibinding является его дополнение с помощью Subcomponents.
 * Subcomponent может доставать экземпляры для Multibinding из родительского Component.
 */

@MustBeDocumented
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class FeatureScope

@Subcomponent(modules = [SecondModule::class])
@FeatureScope
interface SecondComponent {

    val factory: MultiViewModelFactory

    @Subcomponent.Builder
    interface Builder {

        fun build(): SecondComponent
    }
}

@Module
interface SecondModule {

    @Binds
    @[IntoMap ViewModelKey(SecondViewModel::class)]
    fun provideSecondViewModel(secondViewModel: SecondViewModel): ViewModel
}

private fun test2() {
    val appComponent: AppComponent = DaggerAppComponent.create()
    // Выводим класс ViewModel для которых есть фабрики в AppComponent
    println(appComponent.factory.viewModelsClasses.map { it.simpleName })

    val secondComponent: SecondComponent = appComponent.secondComponent.build()
    // Выводим класс ViewModel для которых есть фабрики в SecondComponent (сабкомпонент AppComponent)
    println(secondComponent.factory.viewModelsClasses.map { it.simpleName })

    // Выводим между классами ViewModel для которых есть фабрики в SecondComponent, но нет в AppComponent
    print("Diff=" +
            secondComponent.factory.viewModelsClasses
                .subtract(appComponent.factory.viewModelsClasses)
                .map { it.simpleName }
    )
//    [MainViewModel, DetailsViewModel] <- AppComponent
//    [MainViewModel, DetailsViewModel, SecondViewModel] <- SecondComponent
//    Diff=[SecondViewModel]
}