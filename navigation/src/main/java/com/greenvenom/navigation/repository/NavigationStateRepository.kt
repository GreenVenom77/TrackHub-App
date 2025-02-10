package com.greenvenom.navigation.repository

import com.greenvenom.navigation.domain.NavigationTarget
import com.greenvenom.navigation.data.NavigationType
import com.greenvenom.navigation.utils.AppNavigator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NavigationStateRepository() {
    private val _navigationState = MutableStateFlow(NavigationState())
    val navigationState = _navigationState.asStateFlow()

    private lateinit var appNavigator: AppNavigator<NavigationTarget>
    private lateinit var enableBarsDestinations: Set<NavigationTarget>

    fun config(
        appNavigator: AppNavigator<NavigationTarget>,
        enableBarsDestinations: Set<NavigationTarget>
    ) {
        if (::appNavigator.isInitialized && ::enableBarsDestinations.isInitialized) return
        this.appNavigator = appNavigator
        this.enableBarsDestinations = enableBarsDestinations
    }

    fun updateDestination(navigationType: NavigationType) {
        when(navigationType) {
            is NavigationType.Back -> appNavigator.navigateBack()
            is NavigationType.ClearBackStack -> appNavigator.navigateAndClearBackStack(navigationType.destination)
            is NavigationType.BottomNavigation -> appNavigator.navigateFromBottomBar(navigationType.destination)
            is NavigationType.Standard -> appNavigator.navigateTo(navigationType.destination)
        }
        _navigationState.update {
            it.copy(
                currentDestination = appNavigator.getCurrentDestination(),
                previousDestination = appNavigator.getPreviousDestination()
            )
        }
        updateBarsState()
    }

    private fun updateBarsState() {
        when (_navigationState.value.currentDestination) {
            in enableBarsDestinations -> {
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