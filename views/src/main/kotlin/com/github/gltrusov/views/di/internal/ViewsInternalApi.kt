package com.github.gltrusov.views.di.internal

import com.github.gltrusov.views.di.api.ViewsApi

internal interface ViewsInternalApi : ViewsApi {

    val ViewsDependencyMock: ViewsDependencyMock
}