package com.github.gltrusov.views.di.navigation

import com.github.di_framework.annotation.FeatureApiKey
import com.github.gltrusov.EntryPoint.ActivityEntryPoint
import com.github.gltrusov.OnlyForMainScreen
import com.github.gltrusov.views.R
import com.github.gltrusov.views.di.api.ViewsApi
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@OptIn(OnlyForMainScreen::class)
@Module
class ViewsNavigationModule {

    @Singleton
    @Provides
    fun providerViewsLauncher(): ViewsLauncher = ViewsLauncherImpl()

    @Singleton
    @Provides
    @[IntoMap FeatureApiKey(ViewsApi::class)]
    fun ViewsEntryPoint(
        launcher: ViewsLauncher
    ): ActivityEntryPoint =
        ActivityEntryPoint(
            name = "Views",
            description = "Views, ViewGroups & Custom Views",
            iconResId = R.drawable.views_logo,
            launcher = launcher::launch
        )
}