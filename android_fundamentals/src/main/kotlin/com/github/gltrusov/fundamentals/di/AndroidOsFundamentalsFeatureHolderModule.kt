package com.github.gltrusov.fundamentals.di

import android.content.Context
import com.github.di_framework.annotation.ApplicationContext
import com.github.di_framework.annotation.FeatureApiKey
import com.github.di_framework.core.FeatureContainer
import com.github.di_framework.core.FeatureHolder
import com.github.di_framework.meta.FeatureApi
import com.github.gltrusov.fundamentals.di.api.AndroidOsFundamentalsApi
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
class AndroidOsFundamentalsFeatureHolderModule {
    @Singleton
    @Provides
    @[IntoMap FeatureApiKey(AndroidOsFundamentalsApi::class)]
    fun AndroidOsFundamentalsFeatureHolder(
        @ApplicationContext applicationContext: Context,
        featureContainer: FeatureContainer
    ): FeatureHolder<FeatureApi> {
        return AndroidOsFundamentalsFeatureHolder(
            featureContainer,
            applicationContext
        )
    }
}