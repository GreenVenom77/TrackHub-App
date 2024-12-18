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
                AppDestination.VerifyEmail::class.simpleName -> return AppDestination.VerifyEmail
                AppDestination.OTP::class.simpleName -> return AppDestination.OTP
                AppDestination.ResetPassword::class.simpleName -> return AppDestination.ResetPassword
                else -> null
            }
        }
    return null
}