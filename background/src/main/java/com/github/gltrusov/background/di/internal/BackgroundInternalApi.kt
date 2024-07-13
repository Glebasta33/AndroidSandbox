package com.github.gltrusov.background.di.internal

import com.github.gltrusov.background.di.api.BackgroundApi

internal interface BackgroundInternalApi : BackgroundApi {

    val BackgroundDependencyMock: BackgroundDependencyMock
}