package com.skewnexus.trackhub.di

import com.greenvenom.networking.domain.datasource.RemoteDataSource
import com.trackhub.data.utils.SessionsHandler
import com.trackhub.data.datasource.remote.SupabaseDataSource
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    single {
        SessionsHandler(
            navigationStateRepository = get(),
            sessionStateRepository = get()
        )
    }

    single<RemoteDataSource>(qualifier = named("supabase")) {
        SupabaseDataSource(
            supabaseClient = get()
        )
    }

    single<RemoteDataSource> {
        get(qualifier = named("supabase"))
    }
}