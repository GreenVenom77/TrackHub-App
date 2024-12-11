package com.greevebite.auth.di

import com.greevebite.auth.data.repository.AuthRepositoryImpl
import com.greevebite.auth.domain.repository.AuthRepository
import com.greevebite.auth.login.viewmodel.LoginViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val authModule = module {
    single<AuthRepository> {
        AuthRepositoryImpl(remoteDataSource = get())
    }

    viewModelOf(::LoginViewModel)
}