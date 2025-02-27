package com.skewnexus.trackhub.di

import com.skewnexus.trackhub.navigation.utils.SessionDestinationHandler
import org.koin.dsl.module

val appModule = module {
    single {
        SessionDestinationHandler(
            navigationStateRepository = get(),
            sessionStateRepository = get()
        )
    }
}