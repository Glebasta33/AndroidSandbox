package com.github.gltrusov.test_feature.di.navigation

import com.github.di_framework.annotation.FeatureApiKey
import com.github.gltrusov.EntryPoint.ActivityEntryPoint
import com.github.gltrusov.OnlyForMainScreen
import com.github.gltrusov.core.ui.R
import com.github.gltrusov.test_feature.di.api.TestFeatureApi
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@OptIn(OnlyForMainScreen::class)
@Module
class TestFeatureNavigationModule {

    @Singleton
    @Provides
    fun providerTestFeatureLauncher(): TestFeatureLauncher = TestFeatureLauncherImpl()

    @Singleton
    @Provides
    @[IntoMap FeatureApiKey(TestFeatureApi::class)]
    fun testFeatureEntryPoint(
        launcher: TestFeatureLauncher
    ): ActivityEntryPoint =
        ActivityEntryPoint(
            name = "Feature sample",
            description = "Example of feature module",
            iconResId = R.drawable.ic_baseline_android_24,
            launcher = launcher::launch
        )
}