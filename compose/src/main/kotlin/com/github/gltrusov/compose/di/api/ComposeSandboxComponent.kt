package com.github.gltrusov.compose.di.api

import android.content.Context
import com.github.di_framework.annotation.ApplicationContext
import com.github.di_framework.annotation.PerFeature
import com.github.gltrusov.compose.di.internal.ComposeSandboxInternalApi
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        ComposeSandboxModule::class
    ]
)
@PerFeature
internal interface ComposeSandboxComponent : ComposeSandboxInternalApi {

    @Component.Factory
    interface Factory {
        fun create(
            @ApplicationContext
            @BindsInstance
            applicationContext: Context
        ): ComposeSandboxComponent
    }
}