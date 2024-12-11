package com.greevebite.auth.domain.repository

interface AuthRepository {
    suspend fun registerUser(email: String, password: String, displayName: String)
    suspend fun verifyUserRegistration(email: String, otp: String)
    suspend fun loginUser(email: String, password: String)
    suspend fun logoutUser()
}