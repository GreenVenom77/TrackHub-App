package com.greevebite.trackhub.navigation.mappers

import com.greevebite.trackhub.navigation.AppDestination

fun AppDestination.toRoute(): String {
    return when (this) {
        is AppDestination.Home -> "home"
        is AppDestination.Login -> "login"
        is AppDestination.Register -> "register"
        is AppDestination.ForgotPassword -> "forgot_password"
    }
}