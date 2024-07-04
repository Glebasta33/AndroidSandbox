package com.github.gltrusov.graphics.di.navigation

import com.github.di_framework.annotation.FeatureApiKey
import com.github.gltrusov.EntryPoint.ActivityEntryPoint
import com.github.gltrusov.OnlyForMainScreen
import com.github.gltrusov.graphics.di.api.TestFeatureApi
import com.github.gltrusov.graphics.presentation.TestFeatureActivity
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@OptIn(OnlyForMainScreen::class)
@Module
class TestFeatureNavigationModule {

    @Singleton
    @Provides
    @[IntoMap FeatureApiKey(TestFeatureApi::class)]
    fun testFeatureEntryPoint(): ActivityEntryPoint =
        ActivityEntryPoint(
            name = "Test feature",
            description = "test for build",
            iconResId = com.github.gltrusov.core.ui.R.drawable.ic_baseline_android_24,
            launcher = { context ->
                TestFeatureActivity.createIntent(context)
                    .let(context::startActivity)
            }
        )
}