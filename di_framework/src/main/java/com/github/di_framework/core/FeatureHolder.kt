package com.github.di_framework.core

import com.github.di_framework.meta.Api

/**
 * Держатель фичи, базовая реализация которого способна:
 * - создать Dagger-компонент и передать ему необходимые зависимости,
 * - синхронизировать получение зависимостей из разных потоков,
 * - обеспечить ленивую инициализацию Dagger-компонента и исключить пересоздание.
 */
interface FeatureHolder<T : Api> {

    /** Получить экземпляр фичи */
    fun getFeature(): T

    /** Зануляет экземпляр фичи */
    fun releaseFeature()

}