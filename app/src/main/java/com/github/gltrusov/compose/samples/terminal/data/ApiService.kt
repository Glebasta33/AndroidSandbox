package com.github.gltrusov.compose.samples.terminal.data

import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("aggs/ticker/AAPL/range/{timeframe}/2022-01-09/2023-01-09?adjusted=true&sort=desc&limit=50000&apiKey=cbkLLMgvqwOldRrxdIIhBW_MgeQts4ws")
    suspend fun loadBars(
        @Path("timeframe") timeframe: String
    ): Result

}