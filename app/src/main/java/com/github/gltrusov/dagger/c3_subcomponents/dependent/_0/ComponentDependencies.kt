package com.github.gltrusov.dagger.c3_subcomponents.dependent._0

import android.app.Application
import android.content.Context
import dagger.Component
import dagger.Module

/**
 *  Одна из возвожностей Dagger - указание зависимостей компонента в виде интерфейса или класса,
 *  из которого компонент может брать для себя зависимости.
 *
 *  Чтобы это реализовать, необходимо определить интерфейс или класс, который в себе содержит все необходимые зависимости.
 *
 *  ...
 *  Component dependecies - подход, который позволяет не указывать множество BindsInstance-методов для билдер,
 *  группируая зависимости в один интерфейс. Но более важная роль этого подхода - организация DI при модуляризации:
 *  разделение графов, при котором они не знают друг о друге.
 */
interface AppDeps {
    val context: Context
}

class DaggerApp31 : Application() {

    private lateinit var appComponent: AppComponent31

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent31.builder()
            .appDeps(AppDepsImpl())
            .build()
    }

    /**
     * Реализация интерфейса с зависимостями:
     */
    private inner class AppDepsImpl : AppDeps {
        override val context: Context = this@DaggerApp31
    }
}

/**
 * В аннотации @Component нужно указать интерфейс(-ы), предоставляющие зависимости компоненту.
 */
@Component(
    modules = [AppModule::class],
    dependencies = [AppDeps::class]
)
interface AppComponent31 {

    @Component.Builder
    interface Builder {
        /**
         * Также нужно указать метод билдера, который будет передавать реализацию интерфейса AppDeps,
         * чтобы передавать зависимости в граф при создании компонента.
         */
        fun appDeps(appDeps: AppDeps): Builder

        fun build(): AppComponent31
    }
}

@Module
class AppModule