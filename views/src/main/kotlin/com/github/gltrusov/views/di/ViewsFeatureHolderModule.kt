package com.github.gltrusov.views.di

import android.content.Context
import com.github.di_framework.annotation.ApplicationContext
import com.github.di_framework.annotation.FeatureApiKey
import com.github.di_framework.core.FeatureContainer
import com.github.di_framework.core.FeatureHolder
import com.github.di_framework.meta.FeatureApi
import com.github.gltrusov.views.di.api.ViewsApi
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
class ViewsFeatureHolderModule {
    @Singleton
    @Provides
    @[IntoMap FeatureApiKey(ViewsApi::class)]
    fun ViewsFeatureHolder(
        @ApplicationContext applicationContext: Context,
        featureContainer: FeatureContainer
    ): FeatureHolder<FeatureApi> {
        return ViewsFeatureHolder(featureContainer, applicationContext)
    }
}