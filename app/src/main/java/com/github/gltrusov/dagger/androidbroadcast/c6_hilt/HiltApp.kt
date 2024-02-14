package com.github.gltrusov.dagger.androidbroadcast.c6_hilt

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Нужно просто пометить Application-класс приложения аннотацией @HiltAndroidApp
 * и больше не нужно вручную создавать AppComponent.
 * (Весь код можно убрать!).
 */
@HiltAndroidApp
class HiltApp : Application() {
//
//    lateinit var appComponent: AppComponent
//
//    override fun onCreate() {
//        super.onCreate()
//        appComponent = DaggerAppComponent.create()
//    }
}


//val Context.appComponent: AppComponent
//    get() = when(this) {
//        is HiltApp -> appComponent
//        else -> this.applicationContext.appComponent
//    }