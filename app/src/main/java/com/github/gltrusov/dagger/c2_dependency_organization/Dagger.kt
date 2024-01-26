package com.github.gltrusov.dagger.c2_dependency_organization

import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Named
import javax.inject.Qualifier

@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(activity: DaggerExampleActivity)
}

/**
 * В модули можно вкладывать другие модули.
 */
@Module(includes = [NetworkModule::class, AppBindModule::class])
class AppModule {

    /**
     * Подобные объекты, создаваемые в ручную, проще создавать, навесив @Inject на конструктор.
     */
//    @Provides
//    fun provideNewsRepositoryImpl(
//        newsService: NewsService,
//        analytics: Analytics
//    ): NewsRepositoryImpl {
//        return NewsRepositoryImpl(newsService, analytics)
//    }
//
//    @Provides
//    fun provideAnalytics(): Analytics {
//        return Analytics()
//    }
}

@Module
class NetworkModule {

    /**
     * Так как Retrofit - внешний фреймворк и я не могу навесить @Inject на его конструктор,
     * следует поставлять зависимость через @Provides-метод.
     */
    @Provides
//    @Named("prod")
    @Prod
    fun provideProductionNewsService(): NewsService {
        val retrofit = Retrofit.Builder() // Способ создания - через билдер!
            .baseUrl("https://androidbrodcast.dev")
            .build()
        return retrofit.create()
    }

    /**
     * Если поместить в один Component несколько зависимостей одного и того же типа, это приведёт к ошибке,
     * так как Dagger не сможет определить какой именно объект зависимости нужно инджектить.
     *
     * provideProductionNewsService и provideStageNewsService - возвращают 1 тип, что приводит к конфликту в графе (ERROR: NewsService is bound multiple times).
     * Квалификаторы - это аннотации, который помогают решить этот конфликт.
     * Например, @Named. Затем в месте, где происходит инджет данной зависимости нужно указать Named() с соответсвующим именем.
     *
     * Но подход с Named имеет свои недостатки: нужно где-то хранить строки.
     * Можно создать свои отдельные аннотации-квалификаторы. Named - простая аннотация, помеченная мета-аннотацией @Qualifier.
     *
     * @Qualifier
     * @Documented
     * @Retention(RUNTIME)
     * public @interface Named {
     *
     *     /** The name. */
     *     String value() default "";
     * }
     *
     */
    @Provides
//    @Named("stage")
    /**
     * Если из нескольких provide-методов оставить помеченным Qualifier-аннотацией только 1,
     * то он будет ипспользоваться Dagger`ом по умолчанию, и даже можно не указывать аннотацию при инджекте.
     */
//    @Stage
    fun provideStageNewsService(): NewsService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://stage.androidbrodcast.dev")
            .build()
        return retrofit.create()
    }
}


@Module
interface AppBindModule {

    /**
     * @Binds связывает реализацию с интерфейсом (не нужно прописывать вручную через @Provides).
     * Тк метод абстрактный, можно использовать только в interface или abstract class.
     */
    @Binds
    fun bindNewsRepositoryImplToNewsRepository(
        newsRepositoryImpl: NewsRepositoryImpl
    ): NewsRepository

}

/**
 * Свои отдельные аннотации-квалификаторы:
 */

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Prod

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Stage