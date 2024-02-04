package com.github.gltrusov.dagger.c3_subcomponents.dependent._1


import android.app.Application
import dagger.BindsInstance
import dagger.Component

@Component
interface AppComponent {

    fun application(): Application

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: Application): Builder

        fun build(): AppComponent
    }
}

// ----------------------------------------------------------

/**
 * Более предпочтительный способ организации зависимости между компонентами, чем Subcomponents - Component Dependencies.
 *
 * Допустим нужно получить в FeatureComponent зависимость Application из AppComponent.
 * Для этого нужно добавить dependencies в аннотацию @Component и добавить метод для добавления AppComponent в билдер FeatureComponent.
 * Но в данном примере есть недостаток, не всегда можно прокинуть AppModule в FeatureModule в многомодульном приложении (см. _2)
 */
@Component(dependencies = [AppComponent::class])
interface FeatureComponent {

    @Component.Builder
    interface Builder {

        fun appComponent(appComponent: AppComponent): Builder

        fun build(): FeatureComponent
    }
}