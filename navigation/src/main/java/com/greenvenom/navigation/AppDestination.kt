package com.greenvenom.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface AppDestination {
    @Serializable
    data object Splash: AppDestination

    @Serializable
    data object Login: AppDestination

    @Serializable
    data object Register: AppDestination

    @Serializable
    data object VerifyEmail: AppDestination

    @Serializable
    data object OTP: AppDestination

    @Serializable
    data object NewPassword: AppDestination

    @Serializable
    data object Home: AppDestination

    @Serializable
    data object AIChat: AppDestination

    @Serializable
    data object Doctors: AppDestination

    @Serializable
    data object Sessions: AppDestination

    @Serializable
    data object Profile: AppDestination
}