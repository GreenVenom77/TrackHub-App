package com.greenvenom.auth.data.repository

import com.greenvenom.auth.domain.repository.OtpRepository
import com.greenvenom.networking.domain.datasource.RemoteDataSource

class OtpRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
): OtpRepository {
    override suspend fun verifyOtp(email: String, otp: String) {
        remoteDataSource.verifyOtp(email, otp)
    }
}