package com.github.gltrusov.dagger.c5_multimodularity.app_module.di

import android.app.Application
import com.github.api.NewsServiceApi
import com.github.api.NewsService
import com.github.feature_sample.di.ArticlesDeps
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier
import javax.inject.Scope

/**
 * ## Какие возможности Dagger нужно знать для организации многомодульного приложения.
 *
 * - Component
 * - Component Dependencies - способ передачи зависимостей между компонентами
 * - Scope - помечать компоненты и зависимости
 *
 * Фича (Feature module) - функциональная часть приложения, которая несёт пользовательскую ценность.
 *
 * ## Правила модуляризации
 * 1. Feature модули ничего не знают друго о друге.
 * 2. App модуль соединяет между собой Feature модули.
 * 3. Коммуникация между Feature модулями происходит через app модуль.
 * 4. App модуль предоставляет зависимости для Featue модулей.
 *
 * ## Структура проекта.
 * - Core-модуль (содержит логику, специфичную для моего приложения).
 * - Utils-модуль (логика, которая может быть использована, где угодно).
 * - API-модуль (описание коммуникации с сервером или БД, откуда берутся данные)
 *      - Details фича-модуль
 *      - Favorite фича-модуль
 *      - NewsList фича-модуль
 *          - App - центральный модуль, который отвечает за инициализацию всего приложения и соединение модулей.
 *
 * Упрощённая структура:
 * - Core
 * - API
 *      - NewsList-фича (Gradle dependencies: implementation(project(":core")), implementation(project(":api")))
 *          - App (Gradle dependencies: implementation(project(":feature_sample")), implementation(project(":api")))
 *
 * Для данных модулей нужно создать следующие компоненты:
 * - Утилитарные модули Core, API (без Components, тк они не должны за собой ничего нести)
 *      - FeatureComponent
 *          - AppComponent
 *
 * Направление зависимости между фича модулями и app-модулем:
 * app gradle module -> feature gradle module
 * MainActivity   ->    ArticlesFragment
 * AppComponent    <-   ArticlesComponent
 *
 * AppComponent реализует интерфейс с зависимостями фичи - ArticlesDeps (объявленный в фича-модуле).
 *
 * Если с BaseAppComponent понятно, что его нужно хранить в классе Application, то где хранить ссылку на FeatureComponent,
 * потому что он должен жить пока живёт фича.
 * ## Где хранить ссылку на FeatureComponent?
 * Это может быть Fragment, Activity, Service, ViewModel (+), своё произвольное место.
 */
@[AppScope Component(modules = [BaseAppModule::class])]
interface BaseAppComponent : ArticlesDeps {

    /**
     * Переопределение зависимости из ArticlesDeps
     */
    override val newsService: NewsServiceApi

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun apiKey(@NewsApiQualifier apiKey: String): Builder

        fun build(): BaseAppComponent
    }
}

@Module
class BaseAppModule {

    @[Provides AppScope]
    fun provideNewsService(@NewsApiQualifier apiKey: String) = NewsService(apiKey)
}
@Qualifier
annotation class NewsApiQualifier

@Scope
annotation class AppScope