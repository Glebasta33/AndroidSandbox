package com.github.di_framework.annotation

import com.github.di_framework.meta.FeatureApi
import dagger.MapKey
import kotlin.reflect.KClass

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class FeatureApiKey(val value: KClass<out FeatureApi>)
