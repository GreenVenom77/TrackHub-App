package com.greenvenom.navigation.di

import com.greenvenom.navigation.repository.NavigationStateRepository
import com.greenvenom.navigation.utils.AppNavigator
import org.koin.dsl.module

val navigationModule = module {
    single { AppNavigator() }
    single { NavigationStateRepository() }
}