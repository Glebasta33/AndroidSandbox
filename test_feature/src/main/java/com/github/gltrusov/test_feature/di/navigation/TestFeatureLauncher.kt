package com.github.gltrusov.test_feature.di.navigation

import com.github.di_framework.meta.ProvidedBy
import com.github.gltrusov.ActivityLauncher
import com.github.gltrusov.test_feature.di.api.TestFeatureApi

@ProvidedBy(TestFeatureApi::class)
interface TestFeatureLauncher : ActivityLauncher