package com.github.di_framework.core

import com.github.di_framework.meta.FeatureApi
import kotlin.reflect.KClass

/**
 * Контейнер [FeatureHolder]
 */
interface FeatureContainer {

    /** Получить фичу для непосредственного использования в бизнес-логике приложения */
    fun <Feature : FeatureApi> getFeature(key: KClass<Feature>): Feature

    /** Получить фичу для использования в качестве зависимости при построении других компонентов */
    fun <FeatureDependency : FeatureApi> getDependency(
        key: KClass<FeatureDependency>,
        targetFeatureHolderClass: KClass<*>
    ): FeatureDependency

    /** Удалить экземпляр фичи */
    fun <Feature : FeatureApi> releaseFeature(key: KClass<Feature>)

}