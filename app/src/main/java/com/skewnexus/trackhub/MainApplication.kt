package com.skewnexus.trackhub

import android.app.Application
import com.greenvenom.core_navigation.di.navigationModule
import com.greenvenom.core_network.supabase.di.supabaseModule
import com.skewnexus.trackhub.di.appModule
import com.greenvenom.feat_network.di.networkFeatureModule
import com.greenvenom.feat_auth.di.authFeatureModule
import com.greenvenom.feat_hub.di.hubFeatureModule
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
                networkFeatureModule,
                authFeatureModule,
                hubFeatureModule,
                navigationModule
            )
        }
    }
}