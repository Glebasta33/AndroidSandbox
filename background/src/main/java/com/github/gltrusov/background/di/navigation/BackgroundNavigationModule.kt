package com.github.gltrusov.background.di.navigation

import com.github.di_framework.annotation.FeatureApiKey
import com.github.gltrusov.EntryPoint.ActivityEntryPoint
import com.github.gltrusov.OnlyForMainScreen
import com.github.gltrusov.background.di.api.BackgroundApi
import com.github.gltrusov.background.presentation.BackgroundActivity
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@OptIn(OnlyForMainScreen::class)
@Module
class BackgroundNavigationModule {

    @Singleton
    @Provides
    @[IntoMap FeatureApiKey(BackgroundApi::class)]
    fun BackgroundEntryPoint(): ActivityEntryPoint =
        ActivityEntryPoint(
            name = "Background",
            description = "API`s for background work",
            iconResId = com.github.gltrusov.background.R.drawable.two_gear_icon,
            launcher = { context ->
                BackgroundActivity.createIntent(context)
                    .let(context::startActivity)
            }
        )
}