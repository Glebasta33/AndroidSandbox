package com.github.gltrusov.multithreading.di

import android.content.Context
import com.github.di_framework.annotation.ApplicationContext
import com.github.di_framework.annotation.FeatureApiKey
import com.github.di_framework.core.FeatureContainer
import com.github.di_framework.core.FeatureHolder
import com.github.di_framework.meta.FeatureApi
import com.github.gltrusov.multithreading.di.api.MultithreadingSandboxApi
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
class MultithreadingSandboxFeatureHolderModule {
    @Singleton
    @Provides
    @[IntoMap FeatureApiKey(MultithreadingSandboxApi::class)]
    fun MultithreadingSandboxFeatureHolder(
        @ApplicationContext applicationContext: Context,
        featureContainer: FeatureContainer
    ): FeatureHolder<FeatureApi> {
        return MultithreadingSandboxFeatureHolder(featureContainer, applicationContext)
    }
}