package com.trackhub.domain.datasource

import com.greenvenom.networking.data.Result
import com.greenvenom.networking.domain.Error

interface RemoteDataSource {
    suspend fun registerUser(displayName: String, email: String, password: String): Result<Any?, Error>
    suspend fun loginUser(email: String, password: String): Result<Any, Error>
    suspend fun verifyOtp(email: String, otp: String): Result<Any, Error>
    suspend fun sendResetPasswordEmail(email: String): Result<Any, Error>
    suspend fun updatePassword(password: String): Result<Any, Error>
    suspend fun logoutUser(): Result<Any, Error>
}