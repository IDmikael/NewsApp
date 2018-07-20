package com.idmikael.newstestapp.di.modules

import com.idmikael.newstestapp.ui.activities.AllNewsActivity
import com.idmikael.newstestapp.ui.fragments.AllNewsFragment
import com.idmikael.newstestapp.ui.fragments.NewsFromDatabase
import com.idmikael.newstestapp.ui.fragments.TopHeadlinesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeAllNewsActivity(): AllNewsActivity

    @ContributesAndroidInjector
    abstract fun contributeAllNewsFragment(): AllNewsFragment

    @ContributesAndroidInjector
    abstract fun contributeTopHeadlinesFragment(): TopHeadlinesFragment

    @ContributesAndroidInjector
    abstract fun contributeNewsFromDatabaseFragment(): NewsFromDatabase
}