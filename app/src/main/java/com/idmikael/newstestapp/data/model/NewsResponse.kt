package com.idmikael.newstestapp.data.model

import com.squareup.moshi.Json

data class NewsResponse(
        @Json(name = "articles") val articles: List<NewsResponseItem>
)