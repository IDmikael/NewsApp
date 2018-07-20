package com.idmikael.newstestapp.di.components

import com.idmikael.newstestapp.App
import com.idmikael.newstestapp.di.modules.AppModule
import com.idmikael.newstestapp.di.modules.BuildersModule
import com.idmikael.newstestapp.di.modules.NetModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
        modules = [(AndroidInjectionModule::class), (BuildersModule::class), (AppModule::class), (NetModule::class)]
)
interface AppComponent {
    fun inject(app: App)
}