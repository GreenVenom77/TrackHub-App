package com.greenvenom.navigation.repository

import androidx.compose.runtime.Immutable
import com.greenvenom.navigation.AppDestination

@Immutable
data class NavigationState(
    val currentDestination: AppDestination = AppDestination.Splash,
    val previousDestination: AppDestination = AppDestination.Splash,
    val bottomBarState: Boolean = false,
    val topBarState: Boolean = false
)
