package com.idmikael.newstestapp.data.remote

import com.idmikael.newstestapp.data.model.NewsItem
import com.idmikael.newstestapp.data.model.NewsResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("everything/")
    fun getAllNews(@Query("domains") domains: String, @Query("page") page: Int): Observable<NewsResponse>

    @GET("top-headlines/")
    fun getTopHeadlines(@Query("country") country: String, @Query("page") page: Int): Observable<NewsResponse>
}