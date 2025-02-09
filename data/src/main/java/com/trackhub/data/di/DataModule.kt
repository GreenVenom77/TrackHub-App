package com.trackhub.data.di

import com.trackhub.data.utils.SessionDestinationHandler
import org.koin.dsl.module

val dataModule = module {
    single {
        SessionDestinationHandler(
            navigationStateRepository = get(),
            sessionStateRepository = get()
        )
    }
}