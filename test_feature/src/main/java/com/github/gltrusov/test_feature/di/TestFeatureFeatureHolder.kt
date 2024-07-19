package com.github.gltrusov.test_feature.di

import android.content.Context
import com.github.di_framework.core.BaseFeatureHolder
import com.github.di_framework.core.FeatureContainer
import com.github.gltrusov.test_feature.di.api.DaggerTestFeatureComponent
import com.github.gltrusov.test_feature.di.api.TestFeatureApi

class TestFeatureFeatureHolder(
    featureContainer: FeatureContainer,
    private val applicationContext: Context
) : BaseFeatureHolder<TestFeatureApi>(featureContainer) {
    override fun buildFeature(): TestFeatureApi {
        return DaggerTestFeatureComponent.factory()
            .create(applicationContext)
    }
}