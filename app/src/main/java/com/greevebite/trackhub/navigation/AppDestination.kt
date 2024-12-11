package com.greevebite.trackhub.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface AppDestination {
    @Serializable
    data object Login: AppDestination

    @Serializable
    data object Register: AppDestination

    @Serializable
    data object ForgotPassword: AppDestination

    @Serializable
    data object Home: AppDestination
}