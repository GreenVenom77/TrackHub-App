package com.greenvenom.networking.data.datasource.remote

import com.greenvenom.networking.domain.datasource.RemoteDataSource
import io.github.jan.supabase.auth.OtpType
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class SupabaseDataSource(supabaseClient: SupabaseClient): RemoteDataSource {
    private val client = supabaseClient.getClient()

    override suspend fun registerUser(username: String, email: String, password: String) {
        client.auth.signUpWith(Email) {
            this.email = email
            this.password = password
            this.data = buildJsonObject { put("display_name", username) }
        }
    }

    override suspend fun verifyOtp(email: String, otp: String) {
        client.auth.verifyEmailOtp(OtpType.Email.EMAIL, email, otp)
    }

    override suspend fun resetPassword(email: String, newPassword: String) {
        TODO("Not yet implemented")
    }

    override suspend fun loginUser(email: String, password: String) {
        client.auth.signInWith(Email) {
            this.email = email
            this.password = password
        }
    }

    override suspend fun logoutUser() {
        client.auth.signOut()
    }
}