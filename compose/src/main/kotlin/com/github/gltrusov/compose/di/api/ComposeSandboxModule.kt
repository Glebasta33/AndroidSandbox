package com.github.gltrusov.compose.di.api

import com.github.gltrusov.compose.di.internal.ComposeSandboxInternalModule
import dagger.Module

@Module(
    includes = [
        ComposeSandboxInternalModule::class
    ]
)
class ComposeSandboxModule {
}