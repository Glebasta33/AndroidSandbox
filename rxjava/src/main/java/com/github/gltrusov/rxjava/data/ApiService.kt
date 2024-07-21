package com.github.gltrusov.rxjava.data

import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface ApiService {
    @GET("todos")
    fun getListOfTasks(): Observable<List<TaskDto>>
}