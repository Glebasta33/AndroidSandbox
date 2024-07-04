package com.github.gltrusov.di.entrypoints

import com.github.di_framework.core.BaseFeatureHolder
import com.github.di_framework.core.FeatureContainer
import com.github.gltrusov.di.entrypoints.api.DaggerMainScreenEntryPointsComponent
import com.github.gltrusov.di.entrypoints.api.MainScreenEntryPointsApi

class MainScreenEntryPointsFeatureHolder(
    featureContainer: FeatureContainer
) : BaseFeatureHolder<MainScreenEntryPointsApi>(featureContainer) {

    override fun buildFeature(): MainScreenEntryPointsApi {
        return DaggerMainScreenEntryPointsComponent.create()
    }
}