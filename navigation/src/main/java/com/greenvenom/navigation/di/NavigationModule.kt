package com.greenvenom.navigation.di

import com.greenvenom.navigation.AppNavigator
import com.greenvenom.navigation.repository.NavigationStateRepository
import org.koin.dsl.module

val navigationModule = module {
    single<AppNavigator> { AppNavigator(get()) }
    single<NavigationStateRepository> { NavigationStateRepository() }
}