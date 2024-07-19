package com.github.gltrusov.rxjava.di.api

import android.content.Context
import com.github.di_framework.annotation.ApplicationContext
import com.github.di_framework.annotation.PerFeature
import com.github.gltrusov.rxjava.di.internal.RxJavaInternalApi
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        RxJavaModule::class
    ]
)
@PerFeature
internal interface RxJavaComponent : RxJavaInternalApi {

    @Component.Factory
    interface Factory {
        fun create(
            @ApplicationContext
            @BindsInstance
            applicationContext: Context
        ): RxJavaComponent
    }
}