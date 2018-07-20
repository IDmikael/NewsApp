package com.idmikael.newstestapp.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.PrimaryKey
import com.idmikael.newstestapp.utils.Constants
import com.squareup.moshi.Json

@Entity(
        tableName = Constants.DATABASE_NAME
)
data class NewsItem (
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        val id: Int?,

        @Json(name = "author")
        @ColumnInfo(name = "author")
        val author: String?,

        @Json(name = "title")
        @ColumnInfo(name = "title")
        val title: String?,

        @Json(name = "description")
        @ColumnInfo(name = "description")
        val description: String?,

        @Json(name = "url")
        @ColumnInfo(name = "url")
        val url: String?,

        @Json(name = "urlToImage")
        @ColumnInfo(name = "urlToImage")
        val urlToImage: String?,

        @Json(name = "publishedAt")
        @ColumnInfo(name = "publishedAt")
        val publishedAt: String?,

        @Json(name = "source")
        @ColumnInfo(name = "source")
        val source: String?
)