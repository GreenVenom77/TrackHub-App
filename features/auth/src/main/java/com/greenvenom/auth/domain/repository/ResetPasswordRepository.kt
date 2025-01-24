package com.greenvenom.auth.domain.repository

interface ResetPasswordRepository {
    suspend fun resetPassword(email: String, newPassword: String)
}