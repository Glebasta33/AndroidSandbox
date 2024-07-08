package com.github.gltrusov.compose.di

import android.content.Context
import com.github.di_framework.annotation.ApplicationContext
import com.github.di_framework.annotation.FeatureApiKey
import com.github.di_framework.core.FeatureContainer
import com.github.di_framework.core.FeatureHolder
import com.github.di_framework.meta.FeatureApi
import com.github.gltrusov.compose.di.api.ComposeSandboxApi
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
class ComposeSandboxFeatureHolderModule {
    @Singleton
    @Provides
    @[IntoMap FeatureApiKey(ComposeSandboxApi::class)]
    fun ComposeSandboxFeatureHolder(
        @ApplicationContext applicationContext: Context,
        featureContainer: FeatureContainer
    ): FeatureHolder<FeatureApi> {
        return ComposeSandboxFeatureHolder(featureContainer, applicationContext)
    }
}