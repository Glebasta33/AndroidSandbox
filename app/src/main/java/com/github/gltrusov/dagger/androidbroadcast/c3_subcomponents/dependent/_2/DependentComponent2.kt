package com.github.gltrusov.dagger.androidbroadcast.c3_subcomponents.dependent._2

import android.app.Application
import dagger.BindsInstance
import dagger.Component

/**
 * А компонент с зависимостями можут реализовывать интерфейс.
 * В многомудульном приложении таким образом устраняется связность и прямая зависимость между конкретными компонентами.
 * Это рекомендованный подход в рамках Component Dependecies: использовать интерфейс для передачи зависимостей между компонентами, а не указывать в dependencies компоненты явно.
 */
@Component
interface AppComponent : FeatureDeps {

    override fun application(): Application

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: Application): Builder

        fun build(): AppComponent
    }
}

// ----------------------------------------------------------

/**
 * Подход, принименимый в многомодульном проекте - создание интерфейса с зависимостями.
 */
interface FeatureDeps {

    fun application(): Application

}

/**
 * Можно передавать интерфейс зависимостей (а не компонент напрямую).
 */
@Component(dependencies = [FeatureDeps::class])
interface FeatureComponent {

    @Component.Builder
    interface Builder {

        fun deps(deps: FeatureDeps): Builder

        fun build(): FeatureComponent
    }
}