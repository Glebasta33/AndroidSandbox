package com.github.gltrusov.background.di.internal

import com.github.di_framework.annotation.PerFeature
import dagger.Module
import dagger.Provides

@Module
internal class BackgroundInternalModule {
    @Provides
    @PerFeature
    fun provideBackgroundDependencyMock() = BackgroundDependencyMock
}

internal object BackgroundDependencyMock {
    const val text: String = "BackgroundDependencyMock"
}

