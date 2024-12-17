package com.greevebite.auth.domain.repository

interface LoginRepository {
    suspend fun loginUser(email: String, password: String)
}