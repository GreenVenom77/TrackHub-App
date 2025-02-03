package com.greenvenom.networking.supabase.di

import com.greenvenom.networking.domain.datasource.RemoteDataSource
import com.greenvenom.networking.supabase.SupabaseDataSource
import com.greenvenom.networking.supabase.data.repository.SessionStateRepository
import com.greenvenom.networking.supabase.util.SupabaseClient
import org.koin.core.qualifier.named
import org.koin.dsl.module

val supabaseModule = module {
    single<SupabaseClient> { SupabaseClient() }
    single<SessionStateRepository> { SessionStateRepository(supabaseClient = get()) }

    single<RemoteDataSource>(qualifier = named("supabase")) {
        SupabaseDataSource(
            supabaseClient = get()
        )
    }

    single<RemoteDataSource> {
        get(qualifier = named("supabase"))
    }
}