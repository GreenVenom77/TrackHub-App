package com.greevebite.auth.domain.repository

interface RegisterRepository {
    suspend fun registerUser(email: String, password: String, displayName: String)
}