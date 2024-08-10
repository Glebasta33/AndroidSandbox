package com.github.gltrusov.fundamentals.di.internal

import com.github.di_framework.annotation.PerFeature
import dagger.Module
import dagger.Provides

@Module
internal class AndroidOsFundamentalsInternalModule {
    @Provides
    @PerFeature
    fun provideAndroidOsFundamentalsDependencyMock() = AndroidOsFundamentalsDependencyMock
}

internal object AndroidOsFundamentalsDependencyMock {
    const val text: String = "AndroidOsFundamentalsDependencyMock"
}

