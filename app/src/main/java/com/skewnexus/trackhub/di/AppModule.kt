package com.skewnexus.trackhub.di

import com.skewnexus.trackhub.navigation.SessionsHandler
import org.koin.dsl.module

val appModule = module {
    single {
        SessionsHandler(
            navigationStateRepository = get(),
            sessionStateRepository = get()
        )
    }
}