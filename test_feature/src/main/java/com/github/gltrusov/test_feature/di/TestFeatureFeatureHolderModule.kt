package com.github.gltrusov.test_feature.di

import android.content.Context
import com.github.di_framework.annotation.ApplicationContext
import com.github.di_framework.annotation.FeatureApiKey
import com.github.di_framework.core.FeatureContainer
import com.github.di_framework.core.FeatureHolder
import com.github.di_framework.meta.FeatureApi
import com.github.gltrusov.test_feature.di.api.TestFeatureApi
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
class TestFeatureFeatureHolderModule {
    @Singleton
    @Provides
    @[IntoMap FeatureApiKey(TestFeatureApi::class)]
    fun testFeatureFeatureHolder(
        @ApplicationContext applicationContext: Context,
        featureContainer: FeatureContainer
    ): FeatureHolder<FeatureApi> {
        return TestFeatureFeatureHolder(featureContainer, applicationContext)
    }
}