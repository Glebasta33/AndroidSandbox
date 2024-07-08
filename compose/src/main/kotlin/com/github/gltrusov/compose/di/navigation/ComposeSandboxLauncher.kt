package com.github.gltrusov.compose.di.navigation

import com.github.di_framework.meta.ProvidedBy
import com.github.gltrusov.ActivityLauncher
import com.github.gltrusov.compose.di.api.ComposeSandboxApi

@ProvidedBy(ComposeSandboxApi::class)
interface ComposeSandboxLauncher : ActivityLauncher