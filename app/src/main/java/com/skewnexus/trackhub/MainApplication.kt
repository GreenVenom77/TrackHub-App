package com.skewnexus.trackhub

import android.app.Application
import com.greenvenom.auth.di.authenticationModule
import com.greenvenom.networking.data.datasource.di.dataSourceModule
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
                authenticationModule
            )
        }
    }
}