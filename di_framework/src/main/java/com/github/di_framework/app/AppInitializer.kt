package com.github.di_framework.app

import com.github.di_framework.core.FeatureContainer
import com.github.di_framework.core.FeatureHolder
import com.github.di_framework.meta.FeatureApi
import kotlin.reflect.KClass

interface AppInitializer {

    fun createFeatureHolders(
        featureContainer: FeatureContainer
    ): Map<KClass<out FeatureApi>, FeatureHolder<FeatureApi>>
}