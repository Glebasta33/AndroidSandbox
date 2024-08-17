package com.github.gltrusov.di.feature_holders

import android.app.Application
import android.content.Context
import com.github.di_framework.annotation.ApplicationContext
import com.github.di_framework.annotation.FeatureApiKey
import com.github.di_framework.core.FeatureContainer
import com.github.di_framework.core.FeatureContainerManager
import com.github.di_framework.core.FeatureHolder
import com.github.di_framework.meta.FeatureApi
import com.github.gltrusov.background.di.BackgroundFeatureHolderModule
import com.github.gltrusov.compose.di.ComposeSandboxFeatureHolderModule
import com.github.gltrusov.di.entrypoints.MainScreenEntryPointsFeatureHolder
import com.github.gltrusov.di.entrypoints.api.MainScreenEntryPointsApi
import com.github.gltrusov.fundamentals.di.AndroidOsFundamentalsFeatureHolderModule
import com.github.gltrusov.multithreading.di.MultithreadingSandboxFeatureHolderModule
import com.github.gltrusov.rxjava.di.RxJavaFeatureHolderModule
import com.github.gltrusov.test_feature.di.TestFeatureFeatureHolderModule
import com.github.gltrusov.views.di.ViewsFeatureHolderModule
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module(
    includes = [
        //Здесь указываем модули (featureHolders) фич, которые хотим подключить к приложению
        ComposeSandboxFeatureHolderModule::class,
        BackgroundFeatureHolderModule::class,
        RxJavaFeatureHolderModule::class,
        TestFeatureFeatureHolderModule::class,
        AndroidOsFundamentalsFeatureHolderModule::class,
        ViewsFeatureHolderModule::class,
        MultithreadingSandboxFeatureHolderModule::class
    ]
)
abstract class CommonFeatureHoldersModule {

    @Singleton
    @Binds
    abstract fun bindFeatureContainer(featureContainerManager: FeatureContainerManager): FeatureContainer

    @ApplicationContext
    @Singleton
    @Binds
    abstract fun bindApplicationContext(application: Application): Context

    companion object {

        @Singleton
        @Provides
        @[IntoMap FeatureApiKey(MainScreenEntryPointsApi::class)]
        fun mainScreenEntryPointsFeatureHolder(
            featureContainer: FeatureContainer
        ): FeatureHolder<FeatureApi> {
            return MainScreenEntryPointsFeatureHolder(featureContainer)
        }
    }
}