package com.greenvenom.auth.domain.repository

interface ResetPasswordRepository {
    suspend fun sendResetPasswordEmail(email: String)
    suspend fun updatePassword(newPassword: String)
}