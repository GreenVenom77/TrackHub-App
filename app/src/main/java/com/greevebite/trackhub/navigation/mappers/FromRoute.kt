package com.greevebite.trackhub.navigation.mappers

import androidx.navigation.NavBackStackEntry
import com.greevebite.trackhub.navigation.AppDestination

fun NavBackStackEntry?.fromRoute(): AppDestination? {
    this?.destination?.route?.substringBefore("?")?.substringBefore("/")
        ?.substringAfterLast(".")?.let {
            return when (it) {
                AppDestination.Home::class.simpleName -> return AppDestination.Home
                AppDestination.Login::class.simpleName -> return AppDestination.Login
                AppDestination.Register::class.simpleName -> return AppDestination.Register
                else -> null
            }
        }
    return null
}