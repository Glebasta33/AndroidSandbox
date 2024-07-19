package com.github.gltrusov.rxjava.di

import android.content.Context
import com.github.di_framework.core.BaseFeatureHolder
import com.github.di_framework.core.FeatureContainer
import com.github.gltrusov.rxjava.di.api.DaggerRxJavaComponent
import com.github.gltrusov.rxjava.di.api.RxJavaApi

class RxJavaFeatureHolder(
    featureContainer: FeatureContainer,
    private val applicationContext: Context
) : BaseFeatureHolder<RxJavaApi>(featureContainer) {
    override fun buildFeature(): RxJavaApi {
        return DaggerRxJavaComponent.factory()
            .create(applicationContext)
    }
}