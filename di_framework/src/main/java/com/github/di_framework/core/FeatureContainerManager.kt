package com.github.di_framework.core

import com.github.di_framework.app.AppInitializer

interface FeatureContainerManager : FeatureContainer {

    fun init(appInitializer: AppInitializer): FeatureContainerManager
}