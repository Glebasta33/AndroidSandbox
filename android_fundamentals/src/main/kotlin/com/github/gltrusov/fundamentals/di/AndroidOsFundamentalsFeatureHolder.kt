package com.github.gltrusov.fundamentals.di

import android.content.Context
import com.github.di_framework.core.BaseFeatureHolder
import com.github.di_framework.core.FeatureContainer
import com.github.gltrusov.fundamentals.di.api.DaggerAndroidOsFundamentalsComponent
import com.github.gltrusov.fundamentals.di.api.AndroidOsFundamentalsApi

class AndroidOsFundamentalsFeatureHolder(
    featureContainer: FeatureContainer,
    private val applicationContext: Context
) : BaseFeatureHolder<AndroidOsFundamentalsApi>(featureContainer) {
    override fun buildFeature(): AndroidOsFundamentalsApi {
        return DaggerAndroidOsFundamentalsComponent.factory()
            .create(applicationContext)
    }
}