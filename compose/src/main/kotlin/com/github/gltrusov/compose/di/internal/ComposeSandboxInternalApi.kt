package com.github.gltrusov.compose.di.internal

import com.github.gltrusov.compose.di.api.ComposeSandboxApi

internal interface ComposeSandboxInternalApi : ComposeSandboxApi {

    val ComposeSandboxDependencyMock: ComposeSandboxDependencyMock
}