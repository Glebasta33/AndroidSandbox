package com.github.gltrusov.background.di.api

import android.content.Context
import com.github.di_framework.annotation.ApplicationContext
import com.github.di_framework.annotation.PerFeature
import com.github.gltrusov.background.di.internal.BackgroundInternalApi
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        BackgroundModule::class
    ]
)
@PerFeature
internal interface BackgroundComponent : BackgroundInternalApi {

    @Component.Factory
    interface Factory {
        fun create(
            @ApplicationContext
            @BindsInstance
            applicationContext: Context
        ): BackgroundComponent
    }
}