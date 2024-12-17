package com.greevebite.auth.di

import com.greevebite.auth.data.repository.AccountRepositoryImpl
import com.greevebite.auth.data.repository.LoginRepositoryImpl
import com.greevebite.auth.data.repository.RegisterRepositoryImpl
import com.greevebite.auth.domain.repository.AccountRepository
import com.greevebite.auth.domain.repository.LoginRepository
import com.greevebite.auth.domain.repository.RegisterRepository
import com.greevebite.auth.login.viewmodel.LoginViewModel
import com.greevebite.auth.register.viewmodel.RegisterViewModel
import com.greevebite.auth.AccountViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val authModule = module {
    single<AccountRepository> {
        AccountRepositoryImpl(remoteDataSource = get())
    }

    single<LoginRepository> {
        LoginRepositoryImpl(remoteDataSource = get())
    }

    single<RegisterRepository> {
        RegisterRepositoryImpl(remoteDataSource = get())
    }

    viewModelOf(::AccountViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::RegisterViewModel)
}