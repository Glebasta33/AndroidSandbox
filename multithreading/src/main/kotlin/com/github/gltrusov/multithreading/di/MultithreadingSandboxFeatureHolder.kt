package com.github.gltrusov.multithreading.di

import android.content.Context
import com.github.di_framework.core.BaseFeatureHolder
import com.github.di_framework.core.FeatureContainer
import com.github.gltrusov.multithreading.di.api.DaggerMultithreadingSandboxComponent
import com.github.gltrusov.multithreading.di.api.MultithreadingSandboxApi

class MultithreadingSandboxFeatureHolder(
    featureContainer: FeatureContainer,
    private val applicationContext: Context
) : BaseFeatureHolder<MultithreadingSandboxApi>(featureContainer) {
    override fun buildFeature(): MultithreadingSandboxApi {
        return DaggerMultithreadingSandboxComponent.factory()
            .create(applicationContext)
    }
}