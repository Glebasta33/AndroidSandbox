package com.github.gltrusov.compose.di

import android.content.Context
import com.github.di_framework.core.BaseFeatureHolder
import com.github.di_framework.core.FeatureContainer
import com.github.gltrusov.compose.di.api.DaggerComposeSandboxComponent
import com.github.gltrusov.compose.di.api.ComposeSandboxApi

class ComposeSandboxFeatureHolder(
    featureContainer: FeatureContainer,
    private val applicationContext: Context
) : BaseFeatureHolder<ComposeSandboxApi>(featureContainer) {
    override fun buildFeature(): ComposeSandboxApi {
        return DaggerComposeSandboxComponent.factory()
            .create(applicationContext)
    }
}