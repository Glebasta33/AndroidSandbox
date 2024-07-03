package com.github.gltrusov.di.entrypoints.api

import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        MainScreenEntryPointsModule::class
    ]
)
@Singleton
interface MainScreenEntryPointsComponent : MainScreenEntryPointsApi {
}