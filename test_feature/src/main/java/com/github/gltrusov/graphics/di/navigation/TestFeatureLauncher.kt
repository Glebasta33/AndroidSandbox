package com.github.gltrusov.graphics.di.navigation

import com.github.di_framework.meta.ProvidedBy
import com.github.gltrusov.ActivityLauncher
import com.github.gltrusov.graphics.di.api.TestFeatureApi

@ProvidedBy(TestFeatureApi::class)
interface TestFeatureLauncher : ActivityLauncher