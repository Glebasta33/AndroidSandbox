package com.github.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.http.GET

interface NewsServiceApi {

    @GET("v2/everything?q=android")
    suspend fun everything(): Articles
}

fun NewsService(apiKey: String): NewsServiceApi {
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val authorizedRequest = chain.request().newBuilder()
                .addHeader(HEADER_API_KEY, apiKey)
                .build()
            chain.proceed(authorizedRequest)
        }
        .build()

    val json = Json {
        ignoreUnknownKeys = true
    }


    val retrofit = Retrofit.Builder()
        .baseUrl("https://newsapi.org/")
        .client(okHttpClient)
        .addConverterFactory(json.asConverterFactory(MediaType.parse("application/json")!!))
        .build()

    return object : NewsServiceApi {
        override suspend fun everything(): Articles {
            return Articles(
                articles = listOf(
                    Article(
                        author = "J. Lock",
                        title = "Dagger 2 & DI",
                        description = "Evolution of DI in Android",
                        url = "https://newsapi.org/url",
                        null,
                        "Evolution of DI in Android"
                    ),
                    Article(
                        author = "J. Lock",
                        title = "Dagger 2 & DI",
                        description = "Evolution of DI in Android",
                        url = "https://newsapi.org/url",
                        null,
                        "Evolution of DI in Android"
                    )
                )
            )
        }

    }
        //retrofit.create(NewsServiceApi::class.java)
}

private const val HEADER_API_KEY = "X-Api-Key"