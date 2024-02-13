package com.github.gltrusov.dagger.c6_hilt

import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Inject

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

class NewsRepositoryImpl @Inject constructor(

//    @param:Named("prod")
//    @param:Prod
    @param:Test
    private val newsService: NewsService,
    private val analytics: Analytics,
) : NewsRepository {

    override suspend fun getNews(newsId: String): News {
        analytics.trackNewsRequest(newsId)
        return newsService.news(newsId)
    }
}

class Analytics @Inject constructor(){

    fun trackScreenShow() {}

    fun trackNewsRequest(newsId: String) {
        // Do nothing
    }
}