package com.github.gltrusov.dagger.c5_multimodularity.app_module


import android.app.Application
import com.github.feature_sample.di.ArticlesDepsStore
import com.github.gltrusov.dagger.c5_multimodularity.app_module.di.BaseAppComponent
import com.github.gltrusov.dagger.c5_multimodularity.app_module.di.DaggerBaseAppComponent

class NewsApp : Application() {

    val appComponent: BaseAppComponent by lazy {
        DaggerBaseAppComponent.builder()
            .application(this)
            .apiKey("BuildConfig.NEWS_API_KEY")
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        ArticlesDepsStore.deps = appComponent
    }
}