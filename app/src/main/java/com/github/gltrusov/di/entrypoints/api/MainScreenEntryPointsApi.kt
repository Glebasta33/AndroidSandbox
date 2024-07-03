package com.github.gltrusov.di.entrypoints.api

import com.github.di_framework.meta.FeatureApi
import com.github.gltrusov.EntryPoint
import com.github.gltrusov.OnlyForMainScreen
import kotlin.reflect.KClass

interface MainScreenEntryPointsApi : FeatureApi {

    @OptIn(OnlyForMainScreen::class)
    val mainScreenEntryPointsMap: Map<KClass<out FeatureApi>, EntryPoint.ActivityEntryPoint>
}