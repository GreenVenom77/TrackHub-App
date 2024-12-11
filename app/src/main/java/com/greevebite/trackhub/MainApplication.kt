package com.greevebite.trackhub

import android.app.Application
import com.greevebite.auth.di.authModule
import com.greevebite.data.datasource.di.dataSourceModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            androidLogger()

            modules(
                dataSourceModule,
                authModule
            )
        }
    }
}