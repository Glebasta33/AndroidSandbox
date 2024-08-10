package com.github.gltrusov.fundamentals.di.api

import com.github.gltrusov.fundamentals.di.internal.AndroidOsFundamentalsInternalModule
import dagger.Module

@Module(
    includes = [
        AndroidOsFundamentalsInternalModule::class
    ]
)
class AndroidOsFundamentalsModule {
}