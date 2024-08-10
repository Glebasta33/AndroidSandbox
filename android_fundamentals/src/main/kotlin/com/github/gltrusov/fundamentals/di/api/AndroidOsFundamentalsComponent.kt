package com.github.gltrusov.fundamentals.di.api

import android.content.Context
import com.github.di_framework.annotation.ApplicationContext
import com.github.di_framework.annotation.PerFeature
import com.github.gltrusov.fundamentals.di.internal.AndroidOsFundamentalsInternalApi
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        AndroidOsFundamentalsModule::class
    ]
)
@PerFeature
internal interface AndroidOsFundamentalsComponent : AndroidOsFundamentalsInternalApi {

    @Component.Factory
    interface Factory {
        fun create(
            @ApplicationContext
            @BindsInstance
            applicationContext: Context
        ): AndroidOsFundamentalsComponent
    }
}