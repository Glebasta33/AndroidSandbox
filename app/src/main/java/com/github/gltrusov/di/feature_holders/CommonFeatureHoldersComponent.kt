package com.github.gltrusov.di.feature_holders

import android.app.Application
import com.github.di_framework.core.FeatureContainerManager
import com.github.di_framework.core.FeatureHolder
import com.github.di_framework.meta.FeatureApi
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [CommonFeatureHoldersModule::class]
)
interface CommonFeatureHoldersComponent {

    //Если использовать KClass в качестве ключа, то у даггера возникают проблемы
    fun getFeatureHolders(): Map<Class<out FeatureApi>, @JvmSuppressWildcards FeatureHolder<FeatureApi>>

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application,
            @BindsInstance featureContainerManager: FeatureContainerManager
        ): CommonFeatureHoldersComponent
    }
}