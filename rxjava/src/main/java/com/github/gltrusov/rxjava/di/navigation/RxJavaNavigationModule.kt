package com.github.gltrusov.rxjava.di.navigation

import com.github.di_framework.annotation.FeatureApiKey
import com.github.gltrusov.EntryPoint.ActivityEntryPoint
import com.github.gltrusov.OnlyForMainScreen
import com.github.gltrusov.rxjava.R
import com.github.gltrusov.rxjava.di.api.RxJavaApi
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@OptIn(OnlyForMainScreen::class)
@Module
class RxJavaNavigationModule {

    @Singleton
    @Provides
    fun providerRxJavaLauncher(): RxJavaLauncher = RxJavaLauncherImpl()

    @Singleton
    @Provides
    @[IntoMap FeatureApiKey(RxJavaApi::class)]
    fun rxJavaEntryPoint(
        launcher: RxJavaLauncher
    ): ActivityEntryPoint =
        ActivityEntryPoint(
            name = "RxJava",
            description = "Basics of RxJava",
            iconResId = R.drawable.rxjava,
            launcher = launcher::launch
        )
}