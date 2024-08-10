package com.github.gltrusov.views.di.api

import com.github.gltrusov.views.di.internal.ViewsInternalModule
import dagger.Module

@Module(
    includes = [
        ViewsInternalModule::class
    ]
)
class ViewsModule {
}