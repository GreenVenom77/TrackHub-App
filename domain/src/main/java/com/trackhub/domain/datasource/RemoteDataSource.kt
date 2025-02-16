package com.trackhub.domain.datasource

import com.greenvenom.networking.data.NetworkError
import com.greenvenom.networking.data.NetworkResult

interface RemoteDataSource {
    suspend fun registerUser(displayName: String, email: String, password: String): NetworkResult<Any?, NetworkError>
    suspend fun loginUser(email: String, password: String): NetworkResult<Any, NetworkError>
    suspend fun verifyOtp(email: String, otp: String): NetworkResult<Any, NetworkError>
    suspend fun sendResetPasswordEmail(email: String): NetworkResult<Any, NetworkError>
    suspend fun updatePassword(password: String): NetworkResult<Any, NetworkError>
    suspend fun logoutUser(): NetworkResult<Any, NetworkError>
}