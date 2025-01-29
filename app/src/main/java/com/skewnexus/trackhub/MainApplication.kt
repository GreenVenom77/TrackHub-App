package com.skewnexus.trackhub

import android.app.Application
import com.greenvenom.auth.di.authenticationModule
import com.greenvenom.navigation.di.navigationModule
import com.greenvenom.networking.data.datasource.di.dataSourceModule
import com.skewnexus.trackhub.di.appModule
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
                appModule,
                dataSourceModule,
                authenticationModule,
                navigationModule
            )
        }
    }
}