package com.github.gltrusov.dagger.androidbroadcast.c5_multimodularity


import android.app.Application
import com.github.feature_sample.di.ArticlesDepsStore

class NewsApp : Application() {

    /**
     * Создание компонента app:
     */
    val appComponent: BaseAppComponent by lazy {
        DaggerBaseAppComponent.builder()
            .application(this)
            .apiKey("BuildConfig.NEWS_API_KEY")
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        /**
         * AppComponent кладётся в держатель зависимостей фичи и затем используется для построения графа фичи.
         */
        ArticlesDepsStore.deps = appComponent
    }
}