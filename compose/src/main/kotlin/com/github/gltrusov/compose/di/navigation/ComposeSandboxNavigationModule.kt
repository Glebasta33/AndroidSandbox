package com.github.gltrusov.compose.di.navigation

import com.github.di_framework.annotation.FeatureApiKey
import com.github.gltrusov.EntryPoint.ActivityEntryPoint
import com.github.gltrusov.OnlyForMainScreen
import com.github.gltrusov.compose.R
import com.github.gltrusov.compose.di.api.ComposeSandboxApi
import com.github.gltrusov.compose.presentation.ComposeSandboxActivity
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@OptIn(OnlyForMainScreen::class)
@Module
class ComposeSandboxNavigationModule {

    @Singleton
    @Provides
    @[IntoMap FeatureApiKey(ComposeSandboxApi::class)]
    fun composeSandboxEntryPoint(): ActivityEntryPoint =
        ActivityEntryPoint(
            name = "Compose",
            description = "UI on Jetpack Compose, CustomView, Animations & Effect handlers",
            iconResId = R.drawable.jetpack_compose_icon,
            launcher = { context ->
                //TODO Использовать лаунчер для запуска активити модуля
                ComposeSandboxActivity.createIntent(context)
                    .let(context::startActivity)
            }
        )
}