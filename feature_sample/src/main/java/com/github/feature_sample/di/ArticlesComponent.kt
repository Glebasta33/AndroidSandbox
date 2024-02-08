package com.github.feature_sample.di

import androidx.annotation.RestrictTo
import androidx.lifecycle.ViewModel
import com.github.api.NewsServiceApi
import dagger.Component
import javax.inject.Scope
import kotlin.properties.Delegates.notNull

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class Feature

/**
 * У фичи есть свой компонент, реализующий иньекцию зависимостей в экран фичи.
 * Компонент фичи помечается internal, потому что про этот компонент никто не должен знать, кроме самой фичи.
 *
 * Какие-то зависимости в ArticlesComponent нужно передавать извне (например, нет смысла создавать каждый раз в графе фичи отдельный OkHttpClient).
 * Эти зависимости нужно определить в отдельном интерфейсе.
 * Чтобы передать зависимости в компонент, нужно определить их в интерфейсе ArticlesDeps и передать их в параметр Component(dependencies= )  (поход Component Dependencies).
 */
@[Feature Component(dependencies = [ArticlesDeps::class])]
internal interface ArticlesComponent {

    fun inject(fragment: ArticlesFragment)

    @Component.Builder
    interface Builder {

        /**
         * Реализацию интерфейса с зависимостями нужно передать в биледр компонента.
         */
        fun deps(articlesDeps: ArticlesDeps): Builder

        fun build(): ArticlesComponent
    }
}

/**
 * Интерфейс, в котором объявлены зависимости для фичи - публичный, тк будет
 * реализовываться вовне.
 */
interface ArticlesDeps {

    val newsService: NewsServiceApi
}



/**
 * Если с BaseAppComponent понятно, что его нужно хранить в классе Application, то где хранить ссылку на FeatureComponent, потому что он должен жить пока живёт фича.
 * Где хранить ссылку на FeatureComponent?
 * Это может быть Fragment, Activity, Service, ViewModel (+), своё произвольное место (FeatureHolder?).
 *
 * ArticlesComponentViewModel - это VM, единственная задача которой - сохранять в себе компонент фичи.
 */
internal class ArticlesComponentViewModel : ViewModel() {

    val newDetailsComponent =
        DaggerArticlesComponent.builder().deps(ArticlesDepsProvider.deps).build()
}

/**
 * Этот интерфейс, который кто-то реализует, чтобы предоставлять зависимости.
 */
interface ArticlesDepsProvider {

    @get:RestrictTo(RestrictTo.Scope.LIBRARY)
    val deps: ArticlesDeps

    companion object : ArticlesDepsProvider by ArticlesDepsStore
}

/**
 * Синглтон, реализующий ArticlesDepsProvider для предоставления зависимостей.
 * Свойство deps будет инициализироваться в методе onCreate Application app-модуля.
 */
object ArticlesDepsStore : ArticlesDepsProvider {
    override var deps: ArticlesDeps by notNull()
}