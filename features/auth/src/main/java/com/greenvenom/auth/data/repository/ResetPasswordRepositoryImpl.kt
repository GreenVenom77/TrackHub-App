package com.greenvenom.auth.data.repository

import com.greenvenom.auth.domain.repository.ResetPasswordRepository
import com.greenvenom.networking.domain.datasource.RemoteDataSource

class ResetPasswordRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
): ResetPasswordRepository {
    override suspend fun sendResetPasswordEmail(email: String) {
        remoteDataSource.sendResetPasswordEmail(email)
    }

    override suspend fun updatePassword(newPassword: String) {
        remoteDataSource.updatePassword(password = newPassword)
    }
}