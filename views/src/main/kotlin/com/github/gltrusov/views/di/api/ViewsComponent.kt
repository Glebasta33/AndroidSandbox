package com.github.gltrusov.views.di.api

import android.content.Context
import com.github.di_framework.annotation.ApplicationContext
import com.github.di_framework.annotation.PerFeature
import com.github.gltrusov.views.di.internal.ViewsInternalApi
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        ViewsModule::class
    ]
)
@PerFeature
internal interface ViewsComponent : ViewsInternalApi {

    @Component.Factory
    interface Factory {
        fun create(
            @ApplicationContext
            @BindsInstance
            applicationContext: Context
        ): ViewsComponent
    }
}