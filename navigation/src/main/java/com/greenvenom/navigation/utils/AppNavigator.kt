package com.greenvenom.navigation.utils

import androidx.navigation.NavHostController
import com.greenvenom.navigation.AppDestination
import com.greenvenom.navigation.NavigationTarget
import com.greenvenom.navigation.mappers.fromRoute

class AppNavigator {
    lateinit var navController: NavHostController

    fun addNavController(navController: NavHostController) {
        this.navController = navController
    }

    fun navigateTo(target: NavigationTarget): AppDestination {
        navController.navigate(target) {
            launchSingleTop = true
            restoreState = true
        }
        return getCurrentDestination()
    }

    fun navigateBack(): AppDestination {
        if (hasBackStack()) navController.popBackStack()
        return getCurrentDestination()
    }

    fun navigateAndClearBackStack(target: NavigationTarget): AppDestination {
        navController.navigate(target) {
            popUpTo(0) {
                inclusive = false
            }
            launchSingleTop = true
            restoreState = true
        }
        return getCurrentDestination()
    }

    fun getCurrentDestination(): AppDestination {
        val backstackCurrentDest = navController.currentDestination
        val currentDestination = backstackCurrentDest.fromRoute()

        return currentDestination
    }

    fun getPreviousDestination(): AppDestination {
        val backstackCurrentDest = navController.previousBackStackEntry?.destination
        val currentDestination = backstackCurrentDest.fromRoute()

        return currentDestination
    }

    private fun hasBackStack(): Boolean {

        return navController.currentBackStackEntry != null
    }
}
