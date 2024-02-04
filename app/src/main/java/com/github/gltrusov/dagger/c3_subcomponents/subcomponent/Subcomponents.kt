package com.github.gltrusov.dagger.c3_subcomponents.subcomponent

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Subcomponent
import javax.inject.Scope
import javax.inject.Singleton

fun main(appComponent: AppComponent) {

    /**
     * Сабкомпоненты создаются через инстантс компонента.
     */
    val featureComponent = appComponent.featureComponent()
        .build()
}

/**
 * Чтобы связать компонент с сабкомпонентом, нужно указать у любого модуля компонента ссылку в аннтоации @Module.
 */
@Module(subcomponents = [FeatureComponent::class])
class AppModule

@Component(modules = [AppModule::class])
@Singleton
interface AppComponent {

    /**
     * Также в интерфейсе компонента нужно добавить функцию, создающую сабкомпонент.
     */
    fun featureComponent(): FeatureComponent.Builder

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}


@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class Feature

/**
 * Часто нужно создать компонент (граф) в рамках более крупного (долгоживущего) компонента.
 * Для этих целей в Dagger есть специальный механизм Subcomponent.
 *
 * Компонент и сабкомпоненты имеют жёсткую связь: компонент знает о сабкомпонента и в сгенерированном
 * Dagger`ом коде компонента будет содержаться и код сабкомпонентов.
 * Поэтому подход Subcomponents не будет работать для модуларизации.
 */
@Feature //<- Отдельный скоуп
@Subcomponent(modules = [FeatureModule::class])
interface FeatureComponent {

    @Subcomponent.Builder
    interface Builder {

        fun build(): FeatureComponent
    }
}

@Module
class FeatureModule