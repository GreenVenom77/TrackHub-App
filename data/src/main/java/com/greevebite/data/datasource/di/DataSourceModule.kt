package com.greevebite.data.datasource.di

import com.greevebite.data.datasource.remote.SupabaseClient
import com.greevebite.data.datasource.remote.SupabaseDataSource
import com.greevebite.domain.datasource.RemoteDataSource
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