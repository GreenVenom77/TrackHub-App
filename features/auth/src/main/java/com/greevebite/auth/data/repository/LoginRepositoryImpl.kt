package com.greevebite.auth.data.repository

import com.greevebite.auth.domain.repository.LoginRepository
import com.greevebite.domain.datasource.RemoteDataSource

class LoginRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
): LoginRepository {
    override suspend fun loginUser(email: String, password: String) {
        remoteDataSource.loginUser(email, password)
    }
}