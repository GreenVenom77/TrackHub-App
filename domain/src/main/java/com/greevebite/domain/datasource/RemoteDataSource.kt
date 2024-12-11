package com.greevebite.domain.datasource

interface RemoteDataSource {
    suspend fun registerUser(email: String, password: String, displayName: String)
    suspend fun verifyUserRegistration(email: String, otp: String)
    suspend fun loginUser(email: String, password: String)
    suspend fun logoutUser()
}