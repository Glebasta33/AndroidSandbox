package com.github.gltrusov.views.di

import android.content.Context
import com.github.di_framework.core.BaseFeatureHolder
import com.github.di_framework.core.FeatureContainer
import com.github.gltrusov.views.di.api.DaggerViewsComponent
import com.github.gltrusov.views.di.api.ViewsApi

class ViewsFeatureHolder(
    featureContainer: FeatureContainer,
    private val applicationContext: Context
) : BaseFeatureHolder<ViewsApi>(featureContainer) {
    override fun buildFeature(): ViewsApi {
        return DaggerViewsComponent.factory()
            .create(applicationContext)
    }
}