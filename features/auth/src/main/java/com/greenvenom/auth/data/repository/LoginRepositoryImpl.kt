package com.greenvenom.auth.data.repository

import com.greenvenom.auth.domain.repository.LoginRepository
import com.greenvenom.networking.domain.datasource.RemoteDataSource

class LoginRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
): LoginRepository {
    override suspend fun loginUser(email: String, password: String) {
        remoteDataSource.loginUser(email, password)
    }
}