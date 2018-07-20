package com.idmikael.newstestapp.persistance.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.idmikael.newstestapp.data.model.NewsItem
import com.idmikael.newstestapp.utils.Constants
import io.reactivex.Single

@Dao
interface NewsDao {

    @Query("SELECT * FROM ${Constants.DATABASE_NAME} limit :limit offset :offset")
    fun queryNews(limit: Int, offset: Int): Single<List<NewsItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewsItem(item: NewsItem)

    @Insert(
            onConflict = OnConflictStrategy.REPLACE
    )
    fun insertAllNews(news: List<NewsItem>)
}