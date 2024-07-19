package com.github.gltrusov.rxjava.di

import android.content.Context
import com.github.di_framework.annotation.ApplicationContext
import com.github.di_framework.annotation.FeatureApiKey
import com.github.di_framework.core.FeatureContainer
import com.github.di_framework.core.FeatureHolder
import com.github.di_framework.meta.FeatureApi
import com.github.gltrusov.rxjava.di.api.RxJavaApi
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
class RxJavaFeatureHolderModule {
    @Singleton
    @Provides
    @[IntoMap FeatureApiKey(RxJavaApi::class)]
    fun RxJavaFeatureHolder(
        @ApplicationContext applicationContext: Context,
        featureContainer: FeatureContainer
    ): FeatureHolder<FeatureApi> {
        return RxJavaFeatureHolder(featureContainer, applicationContext)
    }
}