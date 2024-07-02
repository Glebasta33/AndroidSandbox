package com.github.di_framework.init

import com.github.di_framework.core.FeatureHolder
import com.github.di_framework.meta.FeatureApi
import kotlin.reflect.KClass

interface FeatureInitializer {

    fun initialize(): Map<KClass<out FeatureApi>, FeatureHolder<FeatureApi>>
}