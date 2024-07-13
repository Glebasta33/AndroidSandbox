package com.github.gltrusov.background.di

import android.content.Context
import com.github.di_framework.annotation.ApplicationContext
import com.github.di_framework.annotation.FeatureApiKey
import com.github.di_framework.core.FeatureContainer
import com.github.di_framework.core.FeatureHolder
import com.github.di_framework.meta.FeatureApi
import com.github.gltrusov.background.di.api.BackgroundApi
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
class BackgroundFeatureHolderModule {
    @Singleton
    @Provides
    @[IntoMap FeatureApiKey(BackgroundApi::class)]
    fun BackgroundFeatureHolder(
        @ApplicationContext applicationContext: Context,
        featureContainer: FeatureContainer
    ): FeatureHolder<FeatureApi> {
        return BackgroundFeatureHolder(featureContainer, applicationContext)
    }
}