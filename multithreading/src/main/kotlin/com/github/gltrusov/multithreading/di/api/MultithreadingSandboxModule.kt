package com.github.gltrusov.multithreading.di.api

import com.github.gltrusov.multithreading.di.internal.MultithreadingSandboxInternalModule
import dagger.Module

@Module(
    includes = [
        MultithreadingSandboxInternalModule::class
    ]
)
class MultithreadingSandboxModule {
}