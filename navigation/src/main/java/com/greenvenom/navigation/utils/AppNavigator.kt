package com.greenvenom.navigation.utils

import androidx.navigation.NavHostController
import com.greenvenom.navigation.NavigationTarget
import kotlin.reflect.KClass

class AppNavigator<NT : NavigationTarget> {
    lateinit var navController: NavHostController
    private lateinit var navigationTargetType: KClass<out NavigationTarget>

    fun config(
        navController: NavHostController,
        navigationTargetType: KClass<out NavigationTarget>
    ) {
        this.navController = navController
        this.navigationTargetType = navigationTargetType
    }

    fun navigateTo(target: NT): NT? {
        navController.navigate(target) {
            launchSingleTop = true
        }
        return getCurrentDestination()
    }

    fun navigateBack(): NT? {
        if (hasBackStack()) navController.popBackStack()
        return getCurrentDestination()
    }

    fun navigateAndClearBackStack(target: NT): NT? {
        navController.navigate(target) {
            popUpTo(0) { inclusive = true }
            launchSingleTop = true
        }
        return getCurrentDestination()
    }

    fun navigateFromBottomBar(target: NT): NT? {
        navController.navigate(target) {
            popUpTo(navController.graph.startDestinationId) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
        return getCurrentDestination()
    }

    fun getCurrentDestination(): NT? {
        val currentDest = navController.currentBackStackEntry?.destination
        return currentDest.toNavigationTarget(navigationTargetType)
    }

    fun getPreviousDestination(): NT? {
        val previousDest = navController.previousBackStackEntry?.destination
        return previousDest.toNavigationTarget(navigationTargetType)
    }

    private fun hasBackStack(): Boolean {
        return navController.currentBackStackEntry != null
    }
}
