package com.greenvenom.networking.supabase.di

import com.greenvenom.networking.supabase.data.repository.SessionStateRepository
import com.greenvenom.networking.supabase.util.SupabaseClient
import org.koin.dsl.module

val supabaseModule = module {
    single<SupabaseClient> { SupabaseClient() }
    single<SessionStateRepository> { SessionStateRepository(supabaseClient = get()) }
}