package com.github.gltrusov.views.di.navigation

import com.github.di_framework.meta.ProvidedBy
import com.github.gltrusov.ActivityLauncher
import com.github.gltrusov.views.di.api.ViewsApi

@ProvidedBy(ViewsApi::class)
interface ViewsLauncher : ActivityLauncher