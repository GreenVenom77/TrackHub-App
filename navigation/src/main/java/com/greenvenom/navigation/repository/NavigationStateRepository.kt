package com.greenvenom.navigation.repository

import com.greenvenom.navigation.AppDestination
import com.greenvenom.navigation.NavigationTarget
import com.greenvenom.navigation.utils.AppNavigator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.koin.compose.koinInject

class NavigationStateRepository(private val appNavigator: AppNavigator) {
    private val _navigationState = MutableStateFlow(NavigationState())
    val navigationState = _navigationState.asStateFlow()

    fun updateCurrentDestination(
        isNavigatingBack: Boolean = false,
        isClearingBackStack: Boolean = false,
        wantedDestination: NavigationTarget? = null
    ) {
        when {
            isNavigatingBack -> appNavigator.navigateBack()
            isClearingBackStack && wantedDestination != null -> appNavigator.navigateAndClearBackStack(wantedDestination)
            wantedDestination != null -> appNavigator.navigateTo(wantedDestination)
        }
        _navigationState.update {
            it.copy(
                currentDestination = appNavigator.getCurrentDestination(),
                previousDestination = appNavigator.getPreviousDestination()
            )
        }
        updateBarsState(_navigationState.value.currentDestination)
    }

    private fun updateBarsState(currentDestination: AppDestination) {
        when (currentDestination) {
            AppDestination.Home,
            AppDestination.AIChat,
            AppDestination.Sessions,
            AppDestination.Doctors -> {
                _navigationState.update {
                    it.copy(
                        bottomBarState = true,
                        topBarState = true
                    )
                }
            }

            else -> {
                _navigationState.update {
                    it.copy(
                        bottomBarState = false,
                        topBarState = false
                    )
                }
            }
        }
    }
}