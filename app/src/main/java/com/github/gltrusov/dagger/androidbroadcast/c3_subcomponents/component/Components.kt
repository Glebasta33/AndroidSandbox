package com.github.gltrusov.dagger.androidbroadcast.c3_subcomponents.component

import android.app.Application
import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier
import javax.inject.Scope

class DaggerApp3 : Application() {

    private lateinit var appComponent: AppComponent3

    override fun onCreate() {
        super.onCreate()
        /**
         * Помимо стандартного метода create() у Dagger-компонента есть дефолтный метод builder().
         * Метод builder() позволяет передать внешние параметры и кастомизировать создание компонента (например, заранее добавить готовые модули, объекты, и т.д.).
         */
        appComponent = DaggerAppComponent3.builder()
//            .appModule(AppModule(this)) <- DEPRECATED
            .context(this)
            .appId("dev.androidbroadcast.dagger.components")
            .mainHost("https://androidbroadcast.dev/dagger/api")
            .build()  //.create()
    }
}

@Component(modules = [AppModule::class])
@AppScope
interface AppComponent3 {

    /**
     * Можно создать свой кастомный билдер, пометив интерфейс аннотацией @Builder (название не важно).
     * Причем для компонента нужно использовать @Component.Builder, а для сабкомпонента - @Subomponent.Builder
     */
    @Component.Builder
    interface Builder {
        /**
         * Название метода может быть любым, главное, чтобы у него не было параметров и он возвращал тип компонента.
         */
        fun build(): AppComponent3

//        fun appModule(appModule: AppModule): Builder <- DEPRECATED

        /**
         * Более современный подход - @BindsInstance, который позволяет передать аргумент в граф компонента.
         * Все методы билдера кроме create() должны возвращать тип билдера (что соответсвует паттерну "Строитель").
         */
        @BindsInstance
        fun context(context: Context): Builder

        /**
         * Если есть несколько @BindsInstance-методов, принимающих параметры одного типа, их можно
         * отделить с помощнью аннотаций-квалификаторов (как и в случае с @Binds).
         */
        @BindsInstance
        fun appId(@AppId appId: String): Builder
        @BindsInstance
        fun mainHost(@MainHost host: String): Builder
    }

}

/**
 * Раньше использовался способ передачи параметра в конструктор модуля. Но сейчас он его применять не рекомендуется.
 */
@Module
class AppModule(
//    private val context: Context
) {
    @Provides
    @AppScope //<- Вообще ResourceManager не стоит использовать. ResourceManager - обыная обёртка над Context.
    fun provideResourceManager(context: Context) = ResourceManager(context)
}


@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class AppId

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class MainHost

/**
 * Скоупы в Dagger позволяют определять как долго существуют какие-либо зависимости.
 * Каждый компонент должен быть помечен каким-то скоупом. Если зависимость внутри компонента помечена той же аннотацией скоупа, что и компонент,
 * это означает, что она будет создана один раз и навсегда в рамках существования текущего скоупа (локальный синглтон).
 *
 * Какие зависимости помечать аннотацией скоупа?
 * Можно выделить statefull и stateless зависимости. Нужно помечать зависимости, обладающие состоянием (statefull),
 * для который критичено иметь один экземпляр.
 *
 * Если помечать каждую зависимость скоупом - не лучшее решение, то как тогда эффективнее хранить объекты,
 * чтобы не создавать их каждый раз, когда они используются часто.
 * Специально для этого создали скоуп @Reusable, который сохраняет живыми зависимости в рамках компонента какое-то время,
 * но нет гарантий, что зависимость не пересоздаться (не использовать со statefull).
 *
 * По умолчанию без аннотаций скоупа - @Provide создаёт новый инстанс при каждом обращении.
 */
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class AppScope