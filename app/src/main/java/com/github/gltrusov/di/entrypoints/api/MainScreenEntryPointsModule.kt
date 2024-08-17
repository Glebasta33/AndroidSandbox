package com.github.gltrusov.di.entrypoints.api

import com.github.di_framework.meta.FeatureApi
import com.github.gltrusov.EntryPoint
import com.github.gltrusov.OnlyForMainScreen
import com.github.gltrusov.background.di.navigation.BackgroundNavigationModule
import com.github.gltrusov.compose.di.navigation.ComposeSandboxNavigationModule
import com.github.gltrusov.fundamentals.di.navigation.AndroidOsFundamentalsNavigationModule
import com.github.gltrusov.multithreading.di.navigation.MultithreadingSandboxNavigationModule
import com.github.gltrusov.rxjava.di.navigation.RxJavaNavigationModule
import com.github.gltrusov.test_feature.di.navigation.TestFeatureNavigationModule
import com.github.gltrusov.views.di.navigation.ViewsNavigationModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import kotlin.reflect.KClass

@OptIn(OnlyForMainScreen::class)
@Module(
    includes = [
        //здесь указываем модули, в которых объявлены фичевые entryPoint-ы, которые мы хотим увидеть
        //на главном экране
        AndroidOsFundamentalsNavigationModule::class,
        MultithreadingSandboxNavigationModule::class,
        ViewsNavigationModule::class,
        ComposeSandboxNavigationModule::class,
        BackgroundNavigationModule::class,
        RxJavaNavigationModule::class,
        TestFeatureNavigationModule::class,
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