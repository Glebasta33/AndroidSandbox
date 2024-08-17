package com.github.gltrusov.multithreading.di.api

import android.content.Context
import com.github.di_framework.annotation.ApplicationContext
import com.github.di_framework.annotation.PerFeature
import com.github.gltrusov.multithreading.di.internal.MultithreadingSandboxInternalApi
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        MultithreadingSandboxModule::class
    ]
)
@PerFeature
internal interface MultithreadingSandboxComponent : MultithreadingSandboxInternalApi {

    @Component.Factory
    interface Factory {
        fun create(
            @ApplicationContext
            @BindsInstance
            applicationContext: Context
        ): MultithreadingSandboxComponent
    }
}