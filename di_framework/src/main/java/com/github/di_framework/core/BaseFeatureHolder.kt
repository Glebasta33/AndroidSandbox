package com.github.di_framework.core

import com.github.di_framework.meta.FeatureApi
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock
import kotlin.reflect.KClass

abstract class BaseFeatureHolder<Feature : FeatureApi>(
    private val featureContainer: FeatureContainer
) : FeatureHolder<Feature> {

    private val _buildFeatureLock: Lock = ReentrantLock()
    private var _feature: Feature? = null

    override fun getFeature(): Feature {
        if (_feature == null) {
            _buildFeatureLock.lock()
            try {
                if (_feature == null) {
                    _feature = buildFeature()
                }
            } finally {
                _buildFeatureLock.unlock()
            }
        }
        return _feature!!
    }

    /**
     * Получить фичу, от которой зависит исходная фича
     */
    protected fun <FeatureDependency : FeatureApi> getDependency(
        key: KClass<FeatureDependency>
    ): FeatureDependency {
        return featureContainer.getDependency(key, this::class)
    }

    override fun releaseFeature() {
        _buildFeatureLock.lock()
        try {
            _feature = null
            destroyDependencies()
        } finally {
            _buildFeatureLock.unlock()
        }
    }

    //region Destroy Dependencies Scope
    protected open fun destroyDependencies() {
        //call releaseDependency(..) inside
    }

    protected fun <FeatureDependency : FeatureApi> releaseDependency(
        key: KClass<FeatureDependency>
    ) {
        featureContainer.releaseFeature(key)
    }
    //endregion

    protected abstract fun buildFeature(): Feature
}