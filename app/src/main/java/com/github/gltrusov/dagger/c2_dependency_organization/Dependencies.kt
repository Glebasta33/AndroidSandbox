package com.github.gltrusov.dagger.c2_dependency_organization

import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Inject
import javax.inject.Named

interface NewsService {


    @GET("news")
    suspend fun news(): List<News>

    @GET("news/{id}")
    suspend fun news(@Path("id") id: String): News

}

data class News(
    val id: String,
    val title: String,
    val body: String,
)


interface NewsRepository {

    suspend fun getNews(newsId: String): News
}

/**
 * ## Inject в конструктор
 *
 * Если класс свой (не создаётся фреймворком), то можно поставлять его в граф, не используя @Provide в модуле.
 *
 * @Inject - та же самая аннотация, которая указывается над полями-зависимостями (например, в Activity),
 * для того, чтобы Component заинджектил зависимости внутрь класса.
 * В данном случае @Inject навешивается на конструктор, а Component выполняет иньекцию в поля конструктора.
 *
 * Каждый Component видит классы, помеченные аннотацией @Inject и может использовать такой класс при создании графа.
 * Также Component будет знать какие зависимости нужно ему передать для создания экемпляра этого класса.
 *
 * Тут и проявляется одно из главных преимущесть DI/Dagger: мне не нужно заботиться о создании зависимостей
 * для своих классов - я делегирую управление этим процессом Dagger`у, в следствии чего меньше связности между
 * классами в система и больше гибкости при масштабировании системы.
 */
class NewsRepositoryImpl @Inject constructor(
    /**
     * Просто @Named - аннатирует все property, @param:Named - только параметр (более точно).
     *
     * Тк только 1 provide-метод помечен Qualifier-аннотацией, он будет использоваться по умолчанию.
     */
//    @param:Named("prod")
//    @param:Prod
    private val newsService: NewsService,
    private val analytics: Analytics,
) : NewsRepository {

    override suspend fun getNews(newsId: String): News {
        analytics.trackNewsRequest(newsId)
        return newsService.news(newsId)
    }
}

/**
 * Даже для класса с пустым конструктором нужно указать @Inject, чтобы Component мог использовать его в графе.
 */
class Analytics @Inject constructor(){

    fun trackScreenShow() {}

    fun trackNewsRequest(newsId: String) {
        // Do nothing
    }
}