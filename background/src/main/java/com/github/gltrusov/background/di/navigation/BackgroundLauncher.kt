package com.github.gltrusov.background.di.navigation

import com.github.di_framework.meta.ProvidedBy
import com.github.gltrusov.ActivityLauncher
import com.github.gltrusov.background.di.api.BackgroundApi

@ProvidedBy(BackgroundApi::class)
interface BackgroundLauncher : ActivityLauncher