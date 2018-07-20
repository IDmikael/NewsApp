package com.idmikael.newstestapp.di.modules

import android.app.Application
import android.arch.lifecycle.ViewModelProvider
import android.arch.persistence.room.Room
import com.idmikael.newstestapp.persistance.dao.NewsDao
import com.idmikael.newstestapp.persistance.local.Database
import com.idmikael.newstestapp.ui.fragments.view_model.AllNewsFragmentViewModelFactory
import com.idmikael.newstestapp.ui.view_model.AllNewsViewModelFactory
import com.idmikael.newstestapp.utils.Constants
import com.idmikael.newstestapp.utils.Utils
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: Application) {
//    companion object {
//        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//                // For example change the table name to the correct one
//                database.execSQL("ALTER TABLE neaus_db RENAME TO news_db")
//            }
//        }
//    }

    @Provides
    @Singleton
    fun provideApplication(): Application = app

    @Provides
    @Singleton
    fun provideNewsDatabase(app: Application): Database = Room.databaseBuilder(app,
            Database::class.java, Constants.DATABASE_NAME)
            /*.addMigrations(MIGRATION_1_2)*/
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideNewsDao(
            database: Database): NewsDao = database.newsDao()

    @Provides
    @Singleton
    fun provideAllNewsViewModelFactory(
            factory: AllNewsViewModelFactory): ViewModelProvider.Factory = factory

    @Provides
    @Singleton
    fun provideAllNewsFragmentViewModelFactory(
            factory: AllNewsFragmentViewModelFactory): ViewModelProvider.Factory = factory

    @Provides
    @Singleton
    fun provideUtils(): Utils = Utils(app)
}