package com.greenvenom.navigation.repository

import androidx.compose.runtime.Immutable
import com.greenvenom.navigation.AppDestination

@Immutable
data class NavigationState(
    val currentDestination: AppDestination = AppDestination.Splash,
    val bottomBarState: Boolean = true,
    val topBarState: Boolean = true
)
