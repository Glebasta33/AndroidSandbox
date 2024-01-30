package com.github.gltrusov.viewmodel.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import javax.inject.Inject

/**
 * Для создания ViewModel с зависимостями, необходимо реализовать свою фабрику и передать её ViewModelProvider.
 *
 * Данный способ подходит для созданий одной ViewModel.
 * Нужно заинджектить зависимости в фабрику, в реализации create создать экземпляр ViewModel,
 * а затем передать данную фабрику в ViewModelProvider в активити.
 *
 *      class DIViewModelFactory @Inject constructor(
 *          private val newsRepository: NewsRepository
 *      ) : ViewModelProvider.Factory  {
 *
 *     override fun <T : ViewModel> create(modelClass: Class<T>): T {
 *         if (modelClass == DIViewModel::class.java) {
 *             return DIViewModel(newsRepository) as T
 *         }
 *         throw RuntimeException("Unknown view model class $modelClass")
 *     }
 * }
 *
 * При добавлении новых ViewModel при таком подходе, нужно будет инжектить все зависимости в конструктор
 * фабрики и добавлять проверку. При большом количестве ViewModel c большим количеством зависимостей
 * у фабрики будет очень много параметров в конструкторе, которые нужно добавлять вручную.
 *
 *      class DIViewModelFactory @Inject constructor(
 *          private val newsRepository: NewsRepository,
 *          private val analytics: Analytics
 *      ) : ViewModelProvider.Factory  {
 *
 *     override fun <T : ViewModel> create(modelClass: Class<T>): T {
 *         if (modelClass == DIViewModel::class.java) {
 *             return DIViewModel(newsRepository) as T
 *         }
 *         if (modelClass == DIViewModel2::class.java) {
 *             return DIViewModel2(analytics) as T
 *         }
 *         throw RuntimeException("Unknown view model class $modelClass")
 *     }
 * }
 *
 * Есть более удобный и гибкий подход (multibinding) - передавать в конструктор фабрики мапу с ViewModel.
 */
class DIViewModelFactory @Inject constructor(
    private val viewModels: @JvmSuppressWildcards Map<String, ViewModel>
) : ViewModelProvider.Factory  {

    /**
     * Тк на конструкторах вьюмоделей навешена аннотация @Inject, Dagger знает как поставить в них зависимости.
     * При мультибайдинге даггер связывает экземпляры вьюмоделей с классом ViewModel и кладёт их в мапу.
     * В методе create из мапы по ключу (имя класса) достаётся соответсвующий экземпляр ViewModel.
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModels[modelClass.simpleName] as T
    }
}

/**
 * Но как именно создать эту коллекцию Map?
 * Для этого нужно создать отдельный модуль, который будет хранить все ViewModels.
 * Dagger имеет возможность передавать зависимости в коллекцию - это называется multibinding.
 */
@Module
interface ViewModelModule {

    /**
     * Конкретная реализация связывается с базовым классом ViewModel, который и используется в мапе.
     * Тут 2 Bind-метода возвращают один и тот же тип, и под дефолту Dagger выдаст ошибку, тк это конфликт.
     * Но есть способ передавать из каждого Bind-метода значения в одну мапу - аннотация @IntoMap,
     * также нужно указать ключ (аннотация StringKey)
     */
    @Binds
    @IntoMap
    @StringKey("DIViewModel")
    fun bindDIViewModel(impl: DIViewModel): ViewModel

    @Binds
    @IntoMap
    @StringKey("DIViewModel2")
    fun bindDIViewModel2(impl: DIViewModel2): ViewModel
}