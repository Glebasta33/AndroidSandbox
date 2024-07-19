package com.github.gltrusov.rxjava.di.internal

import com.github.gltrusov.rxjava.di.api.RxJavaApi

internal interface RxJavaInternalApi : RxJavaApi {

    val RxJavaDependencyMock: RxJavaDependencyMock
}