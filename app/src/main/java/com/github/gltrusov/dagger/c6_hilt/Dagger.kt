package com.github.gltrusov.dagger.c6_hilt

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Qualifier

/**
 * Цели Dagger Hilt:
 * 1. Сделать работу с Dagger в Android проще (заточен именно под Android SDK и компоненты).
 * 2. Стандартизация Dagger Component (готовый набор стандартных компонентов, в которых уже есть нужные зависимости и скоупы).
 * 3. Иерархия стандартных Dagger-компонентов (Hilt построен на Subcomponents).
 * 4. Упрощение доставки разных зависимостей в разные сборки приложения (build types).
 * 5. Упрощение получения и подмены зависимостей в графе (для тестов).
 *
 * Иерархия компонентов в Hilt:
 *
 * @Singleton
 * SingletonComponent
 *
 * @ActivityRetainedScoped         @ServiceScoped
 * ActivityRetainedComponent       ServiceComponent
 * ...
 * @ViewScoped
 * ViewWithFragmentComponent
 *
 * Если чего-то не хватает, можно добавить свой компонент.
 *
 * Можно легко инджектить основные компоненты Jetpack-библиотек:
 * @HiltViewModel
 * @HiltWorker
 * Инджект в Compose: hiltViewModel()
 *
 * Далее: пример миграции кода 2-го урока на hilt.
 *
 * Не нужно создавать AppComponent, тк есть SingletonComponent
 */
//@Component(modules = [AppModule::class])
//interface AppComponent {
//    fun inject(activity: HiltExampleActivity)
//    fun inject(activity: ViewModelExampleActivity)
//}

/**
 * Чтобы передать зависимости из модуля в компонент, нужно указать компонент в @InstallIn()
 */
@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
//    @Named("prod")
    @Prod
    fun provideProductionNewsService(): NewsService {
        val retrofit = Retrofit.Builder() // Способ создания - через билдер!
            .baseUrl("https://androidbrodcast.dev")
            .build()
        return retrofit.create()
    }

    @Provides
//    @Named("stage")

//    @Stage
    fun provideStageNewsService(): NewsService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://stage.androidbrodcast.dev")
            .build()
        return retrofit.create()
    }

    @Provides
    @Test
    fun provideMockNewsService(): NewsService {
        return object : NewsService {
            override suspend fun news(): List<News> = emptyList()
            override suspend fun news(id: String): News = News("test", "test", "test")
        }
    }
}


@InstallIn(SingletonComponent::class)
@Module
interface AppBindModule {

    @Binds
    fun bindNewsRepositoryImplToNewsRepository(
        newsRepositoryImpl: NewsRepositoryImpl
    ): NewsRepository

}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Prod

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Stage

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Test