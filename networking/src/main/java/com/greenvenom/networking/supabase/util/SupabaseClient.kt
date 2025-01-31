package com.greenvenom.networking.supabase.util

import com.greenvenom.networking.BuildConfig
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

class SupabaseClient {
    private val client = createSupabaseClient(
        supabaseUrl = BuildConfig.BASE_URL,
        supabaseKey = BuildConfig.API_KEY,
    ) {
        install(Postgrest)
        install(Auth)
    }

    fun getClient() = client
}