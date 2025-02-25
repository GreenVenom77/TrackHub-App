package com.skewnexus.trackhub

import android.app.Application
import com.greenvenom.auth.di.authenticationModule
import com.greenvenom.navigation.di.navigationModule
import com.greenvenom.networking.supabase.di.supabaseModule
import com.skewnexus.trackhub.di.appModule
import com.trackhub.hub.di.hubModule
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
                supabaseModule,
                authenticationModule,
                hubModule,
                navigationModule
            )
        }
    }
}