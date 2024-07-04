package com.github.gltrusov.graphics.di.internal

import com.github.gltrusov.graphics.di.api.TestFeatureApi

internal interface TestFeatureInternalApi : TestFeatureApi {

    val testFeatureDependencyMock: TestFeatureDependencyMock
}