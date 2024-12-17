package com.greevebite.auth.domain.repository

interface AccountRepository {
    suspend fun logoutUser()
}