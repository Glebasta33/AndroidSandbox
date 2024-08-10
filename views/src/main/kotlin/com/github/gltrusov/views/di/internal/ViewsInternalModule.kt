package com.github.gltrusov.views.di.internal

import com.github.di_framework.annotation.PerFeature
import dagger.Module
import dagger.Provides

@Module
internal class ViewsInternalModule {
    @Provides
    @PerFeature
    fun provideViewsDependencyMock() = ViewsDependencyMock
}

internal object ViewsDependencyMock {
    const val text: String = "ViewsDependencyMock"
}

