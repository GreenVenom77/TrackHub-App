package com.greenvenom.navigation.utils

import androidx.navigation.NavHostController
import com.greenvenom.navigation.AppDestination
import com.greenvenom.navigation.NavigationTarget
import com.greenvenom.navigation.mappers.fromRoute

class AppNavigator(navControllerHolder: NavHostControllerHolder) {
    private val navController: NavHostController = navControllerHolder.navController

    fun navigateTo(target: NavigationTarget): AppDestination {
        navController.navigate(target) {
            launchSingleTop = true
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
                inclusive = true
            }
            launchSingleTop = true
        }
        return getCurrentDestination()
    }

    fun getCurrentDestination(): AppDestination {
        val backstackCurrentDest = navController.currentDestination
        val currentDestination = backstackCurrentDest.fromRoute()

        return currentDestination
    }

    private fun hasBackStack(): Boolean {
        return navController.currentBackStackEntry != null
    }
}
