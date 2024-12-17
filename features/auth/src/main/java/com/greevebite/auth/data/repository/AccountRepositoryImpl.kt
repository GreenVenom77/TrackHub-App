package com.greevebite.auth.data.repository

import com.greevebite.auth.domain.repository.AccountRepository
import com.greevebite.domain.datasource.RemoteDataSource

class AccountRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
): AccountRepository {
    override suspend fun logoutUser() {
        remoteDataSource.logoutUser()
    }
}