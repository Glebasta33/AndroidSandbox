package com.github.gltrusov.dagger.androidbroadcast.c1_di_basics

import android.app.Application
import android.content.Context

/**
 * Важно! Хранения экземпляра компонента - ответственность разработчика. Необходимо самому определять,
 * когда должен существовать и когда должен быть уничтожен компонент (граф зависимостей).
 * Компонент необходимо хранить в правильном месте с соответсвующим скоупом (ЖЦ).
 * Для AppComponent подходящее месте - это класс Application (необходимо указать в манифесте).
 */
class MainApp : Application() {

    lateinit var appComponent: AppComponent

    /**
     * В методе ЖЦ Application необходимо получить реализацию Dagger-компонента.
     */
    override fun onCreate() {
        super.onCreate()
        /**
         * Получение экземпляря графа зависимостей (компонента).
         */
        appComponent = DaggerAppComponent.create()
    }
}

/**
 * Чтобы получить компонент из Application можно кастить контекст, а можно использовать extenstion-property:
 */
val Context.appComponent: AppComponent
    get() = when(this) {
        is MainApp -> appComponent
        else -> this.applicationContext.appComponent
    }