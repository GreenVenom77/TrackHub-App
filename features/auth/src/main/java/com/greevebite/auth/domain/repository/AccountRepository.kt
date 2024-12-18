package com.greevebite.auth.domain.repository

interface AccountRepository {
    suspend fun verifyOtp(email: String, otp: String)
    suspend fun logoutUser()
}