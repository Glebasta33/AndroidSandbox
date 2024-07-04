package com.github.gltrusov.graphics.di.api

import android.content.Context
import com.github.di_framework.annotation.ApplicationContext
import com.github.di_framework.annotation.PerFeature
import com.github.gltrusov.graphics.di.internal.TestFeatureInternalApi
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        TestFeatureModule::class
    ]
)
@PerFeature
internal interface TestFeatureComponent : TestFeatureInternalApi {

    @Component.Factory
    interface Factory {
        fun create(
            @ApplicationContext
            @BindsInstance
            applicationContext: Context
        ): TestFeatureComponent
    }
}