package com.github.gltrusov.test_feature.di.internal

import com.github.gltrusov.test_feature.di.api.TestFeatureApi

internal interface TestFeatureInternalApi : TestFeatureApi {

    val testFeatureDependencyMock: TestFeatureDependencyMock
}