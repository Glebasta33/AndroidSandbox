package com.github.gltrusov.background.di.api

import com.github.gltrusov.background.di.internal.BackgroundInternalModule
import dagger.Module

@Module(
    includes = [
        BackgroundInternalModule::class
    ]
)
class BackgroundModule {
}