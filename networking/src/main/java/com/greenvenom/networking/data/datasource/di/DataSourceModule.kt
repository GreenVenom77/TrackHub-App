package com.greenvenom.networking.data.datasource.di

import com.greenvenom.networking.data.datasource.remote.SupabaseClient
import com.greenvenom.networking.data.datasource.remote.SupabaseDataSource
import com.greenvenom.networking.domain.datasource.RemoteDataSource
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataSourceModule = module {
    single<SupabaseClient> { SupabaseClient() }

    single<RemoteDataSource>(qualifier = named("supabase")) {
        SupabaseDataSource(
            supabaseClient = get()
        )
    }

    single<RemoteDataSource> {
        get(qualifier = named("supabase"))
    }
}