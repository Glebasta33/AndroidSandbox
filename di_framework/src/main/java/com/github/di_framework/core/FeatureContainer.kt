package com.github.di_framework.core

import com.github.di_framework.meta.Api
import com.github.di_framework.meta.ReleasableApi
import kotlin.reflect.KClass

/**
 * Контейнер [FeatureHolder]
 */
interface FeatureContainer {

    /** Получить фичу для непосредственного использования в бизнес-логике приложения */
    fun <T : Api> getFeature(key: KClass<T>): T

    /** Получить фичу для использования в качестве зависимости при построении других компонентов */
    fun <T : Api> getDependency(
        key: KClass<T>,
        targetFeatureHolderClass: Class<*>
    ): T

    /** Удалить экземпляр фичи */
    fun <T : ReleasableApi> releaseFeature(key: KClass<T>)

}