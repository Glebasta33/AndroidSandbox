package com.github.gltrusov.multithreading.di.internal

import com.github.di_framework.annotation.PerFeature
import dagger.Module
import dagger.Provides

@Module
internal class MultithreadingSandboxInternalModule {
    @Provides
    @PerFeature
    fun provideMultithreadingSandboxDependencyMock() = MultithreadingSandboxDependencyMock
}

internal object MultithreadingSandboxDependencyMock {
    const val text: String = "MultithreadingSandboxDependencyMock"
}

