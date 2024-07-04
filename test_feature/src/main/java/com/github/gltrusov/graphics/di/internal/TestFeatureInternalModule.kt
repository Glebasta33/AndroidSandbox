package com.github.gltrusov.graphics.di.internal

import com.github.di_framework.annotation.PerFeature
import dagger.Module
import dagger.Provides

@Module
internal class TestFeatureInternalModule {
    @Provides
    @PerFeature
    fun provideTestFeatureDependencyMock() = TestFeatureDependencyMock
}

internal object TestFeatureDependencyMock {
    const val text: String = "TestFeatureDependencyMock"
}

