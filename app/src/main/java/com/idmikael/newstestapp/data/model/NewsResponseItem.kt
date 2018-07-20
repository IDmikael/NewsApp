package com.idmikael.newstestapp.data.model

import com.squareup.moshi.Json

data class NewsResponseItem (

        @Json(name = "author")
        val author: String?,

        @Json(name = "title")
        val title: String?,

        @Json(name = "description")
        val description: String?,

        @Json(name = "url")
        val url: String?,

        @Json(name = "urlToImage")
        val urlToImage: String?,

        @Json(name = "publishedAt")
        val publishedAt: String?,

        @Json(name = "source")
        val source: NewsSource
)