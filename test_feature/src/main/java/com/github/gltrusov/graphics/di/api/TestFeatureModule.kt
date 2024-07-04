package com.github.gltrusov.graphics.di.api

import com.github.gltrusov.graphics.di.internal.TestFeatureInternalModule
import dagger.Module

@Module(
    includes = [
        TestFeatureInternalModule::class
    ]
)
class TestFeatureModule {
}