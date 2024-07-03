package com.github.gltrusov.di.feature_holders

import android.app.Application
import com.github.di_framework.core.FeatureContainerManager
import com.github.di_framework.core.FeatureHolder
import com.github.di_framework.init.FeatureInitializer
import com.github.di_framework.meta.FeatureApi
import kotlin.reflect.KClass

class CommonFeaturesInitializer(
    private val application: Application,
    private val featureContainerManager: FeatureContainerManager
) : FeatureInitializer {

    override fun initialize(): Map<KClass<out FeatureApi>, FeatureHolder<FeatureApi>> {
        return DaggerCommonFeatureHoldersComponent.factory()
            .create(application, featureContainerManager)
            .getFeatureHolders()
            .mapKeys { it.key.kotlin }
    }
}