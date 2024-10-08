package com.greevebite.trackhub.core.data.remote

import com.greevebite.trackhub.BuildConfig
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.OtpType
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class SupabaseClient {
    private val client = createSupabaseClient(
        supabaseUrl = BuildConfig.SUPABASE_URL,
        supabaseKey = BuildConfig.SUPABASE_KEY,
    ) {
        install(Postgrest)
        install(Auth)
    }

    suspend fun registerUser(email: String, password: String, displayName: String) {
        client.auth.signUpWith(Email) {
            this.email = email
            this.password = password
            this.data = buildJsonObject { put("display_name", displayName) }
        }
    }

    suspend fun verifyUserRegistration(email: String, otp: String) {
        client.auth.verifyEmailOtp(OtpType.Email.EMAIL, email, otp)
    }

    suspend fun loginUser(email: String, password: String) {
        client.auth.signInWith(Email) {
            this.email = email
            this.password = password
        }
    }

    suspend fun logoutUser() {
        client.auth.signOut()
    }
}