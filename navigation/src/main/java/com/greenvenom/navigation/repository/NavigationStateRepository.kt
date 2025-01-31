package com.greenvenom.navigation.repository

import com.greenvenom.navigation.AppDestination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NavigationStateRepository {
    private val _navigationState = MutableStateFlow(NavigationState())
    val navigationState = _navigationState.asStateFlow()

    fun updateCurrentDestination(newDestination: AppDestination) {
        _navigationState.update { it.copy(currentDestination = newDestination) }
        println("Current Destination: $newDestination")

        when (newDestination) {
            AppDestination.Home,
            AppDestination.AIChat,
            AppDestination.Sessions,
            AppDestination.Doctors -> {
                updateBarsState(
                    bottomBarState = true,
                    topBarState = true
                )
            }

            else -> {
                updateBarsState(
                    bottomBarState = false,
                    topBarState = false
                )
            }
        }
    }

    private fun updateBarsState(bottomBarState: Boolean, topBarState: Boolean) {
        _navigationState.update {
            it.copy(
                bottomBarState = bottomBarState,
                topBarState = topBarState
            )
        }
    }
}