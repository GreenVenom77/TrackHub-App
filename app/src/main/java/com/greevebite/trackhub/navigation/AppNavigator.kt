package com.greevebite.trackhub.navigation

import androidx.navigation.NavHostController
import com.greevebite.trackhub.navigation.mappers.fromRoute

class AppNavigator(private val navController: NavHostController) {
    fun navigateTo(destination: AppDestination) {
        navController.navigate(destination) { launchSingleTop = true }
    }

    fun navigateBack(): AppDestination? {
        if (hasBackStack()) navController.popBackStack()
        return getCurrentDestination()
    }

    fun navigateAndClearBackStack(destination: AppDestination) {
        navController.navigate(destination) {
            popUpTo(0) {
                inclusive = true
            }
            launchSingleTop = true
        }
    }

    fun getCurrentDestination(): AppDestination? {
        if (hasBackStack()) {
            val backstackEntry = navController.currentBackStackEntry
            val currentDestination = backstackEntry.fromRoute()

            return currentDestination
        } else return null
    }

    private fun hasBackStack(): Boolean {
        return navController.previousBackStackEntry != null
    }
}
