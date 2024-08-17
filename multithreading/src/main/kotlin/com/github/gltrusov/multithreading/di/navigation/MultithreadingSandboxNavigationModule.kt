package com.github.gltrusov.multithreading.di.navigation

import com.github.di_framework.annotation.FeatureApiKey
import com.github.gltrusov.EntryPoint.ActivityEntryPoint
import com.github.gltrusov.OnlyForMainScreen
import com.github.gltrusov.multithreading.R
import com.github.gltrusov.multithreading.di.api.MultithreadingSandboxApi
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@OptIn(OnlyForMainScreen::class)
@Module
class MultithreadingSandboxNavigationModule {

    @Singleton
    @Provides
    fun provideLauncher(): MultithreadingSandboxLauncher = MultithreadingSandboxLauncherImpl()

    @Singleton
    @Provides
    @[IntoMap FeatureApiKey(MultithreadingSandboxApi::class)]
    fun multithreadingSandboxEntryPoint(
        launcher: MultithreadingSandboxLauncher
    ): ActivityEntryPoint =
        ActivityEntryPoint(
            name = "Multithreading",
            description = "Threads, parallelism & concurrency.",
            iconResId = R.drawable.threads,
            launcher = launcher::launch
        )
}