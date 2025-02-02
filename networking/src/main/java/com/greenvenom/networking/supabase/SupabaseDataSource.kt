package com.greenvenom.networking.supabase

import com.greenvenom.networking.data.Result
import com.greenvenom.networking.supabase.util.SupabaseClient
import com.greenvenom.networking.domain.datasource.RemoteDataSource
import com.greenvenom.networking.supabase.data.SupabaseError
import com.greenvenom.networking.supabase.util.supabaseCall
import io.github.jan.supabase.auth.OtpType
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.user.UserInfo
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class SupabaseDataSource(
    supabaseClient: SupabaseClient
): RemoteDataSource {
    private val client = supabaseClient.getClient()

    override suspend fun registerUser(username: String, email: String, password: String): Result<UserInfo?, SupabaseError> {
        return supabaseCall<UserInfo?> {
            client.auth.signUpWith(Email) {
                this.email = email
                this.password = password
                this.data = buildJsonObject { put("display_name", username) }
            }
        }
    }

    override suspend fun verifyOtp(email: String, otp: String): Result<Unit, SupabaseError> {
        return supabaseCall {
            client.auth.verifyEmailOtp(OtpType.Email.EMAIL, email, otp)
        }
    }

    override suspend fun sendResetPasswordEmail(email: String): Result<Unit, SupabaseError> {
        return supabaseCall {
            client.auth.resetPasswordForEmail(email = email)
        }
    }

    override suspend fun updatePassword(password: String): Result<UserInfo, SupabaseError> {
        return supabaseCall {
            client.auth.updateUser {
                this.password = password
            }
        }
    }

    override suspend fun loginUser(email: String, password: String): Result<Unit, SupabaseError> {
        return supabaseCall {
            client.auth.signInWith(Email) {
                this.email = email
                this.password = password
            }
        }
    }

    override suspend fun logoutUser(): Result<Unit, SupabaseError> {
        return supabaseCall {
            client.auth.signOut()
        }
    }
}