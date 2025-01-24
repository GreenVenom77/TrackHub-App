package com.greenvenom.auth.domain.repository

interface LoginRepository {
    suspend fun loginUser(email: String, password: String)
}