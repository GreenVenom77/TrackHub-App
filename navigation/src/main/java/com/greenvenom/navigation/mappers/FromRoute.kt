package com.greenvenom.navigation.mappers

import androidx.navigation.NavDestination
import com.greenvenom.navigation.AppDestination

fun NavDestination?.fromRoute(): AppDestination {
    this?.route?.substringBefore("?")?.substringBefore("/")
        ?.substringAfterLast(".")?.let {
            return when (it) {
                AppDestination.Splash::class.simpleName -> return AppDestination.Splash
                AppDestination.Login::class.simpleName -> return AppDestination.Login
                AppDestination.Register::class.simpleName -> return AppDestination.Register
                AppDestination.VerifyEmail::class.simpleName -> return AppDestination.VerifyEmail
                AppDestination.OTP::class.simpleName -> return AppDestination.OTP
                AppDestination.NewPassword::class.simpleName -> return AppDestination.NewPassword
                AppDestination.Home::class.simpleName -> return AppDestination.Home
                AppDestination.AIChat::class.simpleName -> return AppDestination.AIChat
                AppDestination.Doctors::class.simpleName -> return AppDestination.Doctors
                AppDestination.Sessions::class.simpleName -> return AppDestination.Sessions
                AppDestination.Profile::class.simpleName -> return AppDestination.Profile
                else -> AppDestination.Splash
            }
        }
    return AppDestination.Splash
}