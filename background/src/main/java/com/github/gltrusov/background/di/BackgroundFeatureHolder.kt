package com.github.gltrusov.background.di

import android.content.Context
import com.github.di_framework.core.BaseFeatureHolder
import com.github.di_framework.core.FeatureContainer
import com.github.gltrusov.background.di.api.DaggerBackgroundComponent
import com.github.gltrusov.background.di.api.BackgroundApi

class BackgroundFeatureHolder(
    featureContainer: FeatureContainer,
    private val applicationContext: Context
) : BaseFeatureHolder<BackgroundApi>(featureContainer) {
    override fun buildFeature(): BackgroundApi {
        return DaggerBackgroundComponent.factory()
            .create(applicationContext)
    }
}