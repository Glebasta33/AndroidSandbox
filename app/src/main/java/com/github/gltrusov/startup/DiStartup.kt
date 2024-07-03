package com.github.gltrusov.startup

import android.content.Context
import com.github.di_framework.app.AppInitializerImpl
import com.github.di_framework.core.DI
import com.github.di_framework.core.FeatureContainer
import com.github.di_framework.core.FeatureContainerManager
import com.github.di_framework.core.FeatureContainerManagerImpl
import com.github.gltrusov.SandboxApplication
import com.github.gltrusov.di.feature_holders.CommonFeaturesInitializer

class DiStartup {

    fun create(context: Context) = try {
        val application = context as SandboxApplication
        val featureContainer: FeatureContainer = buildFeatureContainer(application)
        DI.initialize(featureContainer)
    } catch (e: Exception) {
        throw RuntimeException("Failed initialize DI", e)
    }

    private fun buildFeatureContainer(application: SandboxApplication): FeatureContainer {
        val featureContainerManager: FeatureContainerManager = FeatureContainerManagerImpl()
        return featureContainerManager.init(
            AppInitializerImpl(
                CommonFeaturesInitializer(application, featureContainerManager)
            )
        )
    }
}