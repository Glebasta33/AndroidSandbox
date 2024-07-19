package com.github.gltrusov.test_feature.di.api

import com.github.gltrusov.test_feature.di.internal.TestFeatureInternalModule
import dagger.Module

@Module(
    includes = [
        TestFeatureInternalModule::class
    ]
)
class TestFeatureModule {
}