package com.idmikael.newstestapp.data.model

import com.squareup.moshi.Json

data class NewsSource (
        @Json(name = "id")
        val id: String?,

        @Json(name = "name")
        val name: String?
)