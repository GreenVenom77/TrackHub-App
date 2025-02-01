package com.greenvenom.networking.supabase

import com.greenvenom.networking.supabase.util.SupabaseClient
import com.greenvenom.networking.domain.datasource.RemoteDataSource
import io.github.jan.supabase.auth.OtpType
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.status.SessionSource
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class SupabaseDataSource(
    supabaseClient: SupabaseClient
): RemoteDataSource {
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

    override suspend fun sendResetPasswordEmail(email: String) {
        client.auth.resetPasswordForEmail(email = email)
    }

    override suspend fun updatePassword(password: String) {
        client.auth.updateUser {
            this.password = password
        }
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