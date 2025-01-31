package com.greenvenom.navigation.di

import com.greenvenom.navigation.utils.AppNavigator
import com.greenvenom.navigation.repository.NavigationStateRepository
import com.greenvenom.navigation.utils.NavHostControllerHolder
import org.koin.dsl.module

val navigationModule = module {
    single { NavHostControllerHolder() }
    single { AppNavigator(get()) }
    single { NavigationStateRepository() }
}