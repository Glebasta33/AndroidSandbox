package com.github.gltrusov.rxjava.di.api

import com.github.gltrusov.rxjava.di.internal.RxJavaInternalModule
import dagger.Module

@Module(
    includes = [
        RxJavaInternalModule::class
    ]
)
class RxJavaModule {
}