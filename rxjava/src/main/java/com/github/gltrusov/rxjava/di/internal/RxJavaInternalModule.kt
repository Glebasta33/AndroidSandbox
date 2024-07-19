package com.github.gltrusov.rxjava.di.internal

import com.github.di_framework.annotation.PerFeature
import dagger.Module
import dagger.Provides

@Module
internal class RxJavaInternalModule {
    @Provides
    @PerFeature
    fun provideRxJavaDependencyMock() = RxJavaDependencyMock
}

internal object RxJavaDependencyMock {
    const val text: String = "RxJavaDependencyMock"
}

