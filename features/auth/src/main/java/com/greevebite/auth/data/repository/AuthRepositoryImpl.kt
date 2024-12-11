package com.greevebite.auth.data.repository

import com.greevebite.domain.datasource.RemoteDataSource

class AuthRepositoryImpl(val remoteDataSource: RemoteDataSource):
    com.greevebite.auth.domain.repository.AuthRepository {
    override suspend fun registerUser(
        email: String,
        password: String,
        displayName: String
    ) {
        remoteDataSource.registerUser(email, password, displayName)
    }

    override suspend fun verifyUserRegistration(email: String, otp: String) {
        remoteDataSource.verifyUserRegistration(email, otp)
    }

    override suspend fun loginUser(email: String, password: String) {
        remoteDataSource.loginUser(email, password)
    }

    override suspend fun logoutUser() {
        remoteDataSource.logoutUser()
    }

}