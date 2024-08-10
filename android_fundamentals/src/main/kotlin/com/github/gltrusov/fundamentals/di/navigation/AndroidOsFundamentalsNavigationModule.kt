package com.github.gltrusov.fundamentals.di.navigation

import com.github.di_framework.annotation.FeatureApiKey
import com.github.gltrusov.EntryPoint.ActivityEntryPoint
import com.github.gltrusov.OnlyForMainScreen
import com.github.gltrusov.core.ui.R
import com.github.gltrusov.fundamentals.di.api.AndroidOsFundamentalsApi
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@OptIn(OnlyForMainScreen::class)
@Module
class AndroidOsFundamentalsNavigationModule {

    @Singleton
    @Provides
    fun providerAndroidOsFundamentalsLauncher(): AndroidOsFundamentalsLauncher = AndroidOsFundamentalsLauncherImpl()

    @Singleton
    @Provides
    @[IntoMap FeatureApiKey(AndroidOsFundamentalsApi::class)]
    fun AndroidOsFundamentalsEntryPoint(
        launcher: AndroidOsFundamentalsLauncher
    ): ActivityEntryPoint =
        ActivityEntryPoint(
            name = "Android OS fundamentals",
            description = "Core android components & internals",
            iconResId = R.drawable.ic_baseline_android_green_24,
            launcher = launcher::launch
        )
}