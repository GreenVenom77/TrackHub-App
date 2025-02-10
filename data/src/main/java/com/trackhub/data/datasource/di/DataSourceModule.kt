package com.trackhub.data.datasource.di

import com.trackhub.domain.datasource.RemoteDataSource
import com.trackhub.data.datasource.remote.SupabaseDataSource
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataSourceModule = module {
    single<RemoteDataSource>(qualifier = named<SupabaseDataSource>()) {
        SupabaseDataSource(
            supabaseClient = get()
        )
    }

    single<RemoteDataSource> {
        get(qualifier = named<SupabaseDataSource>())
    }
}