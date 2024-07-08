package com.github.gltrusov.compose.di.internal

import com.github.di_framework.annotation.PerFeature
import dagger.Module
import dagger.Provides

@Module
internal class ComposeSandboxInternalModule {
    @Provides
    @PerFeature
    fun provideComposeSandboxDependencyMock() = ComposeSandboxDependencyMock
}

internal object ComposeSandboxDependencyMock {
    const val text: String = "ComposeSandboxDependencyMock"
}

