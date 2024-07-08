package com.github.gltrusov.di.entrypoints.api

import com.github.di_framework.meta.FeatureApi
import com.github.gltrusov.EntryPoint
import com.github.gltrusov.OnlyForMainScreen
import com.github.gltrusov.compose.di.navigation.ComposeSandboxNavigationModule
import com.github.gltrusov.graphics.di.navigation.TestFeatureNavigationModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import kotlin.reflect.KClass

@OptIn(OnlyForMainScreen::class)
@Module(
    includes = [
        //здесь указываем модули, в которых объявлены фичевые entryPoint-ы, которые мы хотим увидеть
        //на главном экране
        TestFeatureNavigationModule::class,
        ComposeSandboxNavigationModule::class
    ]
)
class MainScreenEntryPointsModule {

    @Singleton
    @Provides
    fun entryPointsMap(
        source: Map<Class<out FeatureApi>, @JvmSuppressWildcards EntryPoint.ActivityEntryPoint>
    ): Map<KClass<out FeatureApi>, EntryPoint.ActivityEntryPoint> {
        return source.mapKeys { it.key.kotlin }
    }
}