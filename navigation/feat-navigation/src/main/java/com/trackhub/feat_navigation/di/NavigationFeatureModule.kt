package com.trackhub.feat_navigation.di

import com.trackhub.feat_navigation.data.DestinationHandler
import org.koin.dsl.module

val navigationFeatureModule = module {
    single {
        DestinationHandler(
            navigationStateRepository = get(),
            sessionStateRepository = get()
        )
    }
}