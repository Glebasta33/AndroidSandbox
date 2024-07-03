package com.github.gltrusov.di.entrypoints.api

import com.github.di_framework.meta.FeatureApi
import dagger.Module
import dagger.Provides
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories
import javax.inject.Singleton
import kotlin.reflect.KClass

//@OptIn(OnlyForMainScreen::class)
@Module(
    includes = [
        //здесь указываем модули, в которых объявлены фичевые entryPoint-ы, которые мы хотим увидеть
        //на главном экране
    ]
)
class MainScreenEntryPointsModule {

    @Singleton
    @Provides
    fun entryPointsMap(
        source: Map<Class<out FeatureApi>, @JvmSuppressWildcards DefaultViewModelFactories.ActivityEntryPoint>
    ): Map<KClass<out FeatureApi>, DefaultViewModelFactories.ActivityEntryPoint> {
        return source.mapKeys { it.key.kotlin }
    }
}