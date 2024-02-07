package com.github.api

import kotlinx.serialization.Serializable

@Serializable
data class Articles(
    val articles: List<Article>
)

@Serializable
data class Article(
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val content: String?,
)