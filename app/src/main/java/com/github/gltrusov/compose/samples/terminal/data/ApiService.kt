package com.github.gltrusov.compose.samples.terminal.data

import retrofit2.http.GET

interface ApiService {

    @GET("aggs/ticker/AAPL/range/1/day/2022-01-09/2023-01-09?adjusted=true&sort=asc&limit=50000&apiKey=cbkLLMgvqwOldRrxdIIhBW_MgeQts4ws")
    fun loadBars()

}