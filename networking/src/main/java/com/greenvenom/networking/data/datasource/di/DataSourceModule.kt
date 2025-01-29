package com.greenvenom.networking.data.datasource.di

import com.greenvenom.networking.data.datasource.supabase.SessionStateRepository
import com.greenvenom.networking.data.datasource.supabase.util.SupabaseClient
import com.greenvenom.networking.data.datasource.supabase.SupabaseDataSource
import com.greenvenom.networking.domain.datasource.RemoteDataSource
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataSourceModule = module {
    single<SupabaseClient> { SupabaseClient() }
    single<SessionStateRepository> { SessionStateRepository(supabaseClient = get()) }

    single<RemoteDataSource>(qualifier = named("supabase")) {
        SupabaseDataSource(
            supabaseClient = get(),
            sessionStateRepository = get()
        )
    }

    single<RemoteDataSource> {
        get(qualifier = named("supabase"))
    }
}