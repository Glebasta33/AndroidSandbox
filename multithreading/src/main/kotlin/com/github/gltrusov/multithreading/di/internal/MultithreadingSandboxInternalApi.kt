package com.github.gltrusov.multithreading.di.internal

import com.github.gltrusov.multithreading.di.api.MultithreadingSandboxApi

internal interface MultithreadingSandboxInternalApi : MultithreadingSandboxApi {

    val multithreadingDependencyMock: MultithreadingSandboxDependencyMock
}