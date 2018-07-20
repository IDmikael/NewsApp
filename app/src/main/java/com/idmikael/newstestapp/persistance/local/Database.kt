package com.idmikael.newstestapp.persistance.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.idmikael.newstestapp.data.model.NewsItem
import com.idmikael.newstestapp.persistance.dao.NewsDao

@Database (entities = [(NewsItem::class)], version = 2)
abstract class Database : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}