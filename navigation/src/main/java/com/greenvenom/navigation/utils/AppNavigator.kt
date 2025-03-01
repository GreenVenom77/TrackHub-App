package com.greenvenom.navigation.utils

import androidx.navigation.NavHostController
import com.greenvenom.navigation.domain.NavigationTarget
import kotlin.reflect.KClass

class AppNavigator {
    private lateinit var navigationType: KClass<out NavigationTarget>
    lateinit var navController: NavHostController
        private set

    fun config(
        navigationType: KClass<out NavigationTarget>,
        navController: NavHostController
    ) {
        this.navigationType = navigationType
        this.navController = navController
    }

    fun navigateTo(target: NavigationTarget): NavigationTarget? {
        navController.navigate(target) {
            launchSingleTop = true
        }
        return getCurrentDestination()
    }

    fun navigateBack(): NavigationTarget? {
        navController.navigateUp()
        return getCurrentDestination()
    }

    fun navigateAndClearBackStack(target: NavigationTarget): NavigationTarget? {
        navController.navigate(target) {
            popUpTo(0) { inclusive = true }
            launchSingleTop = true
        }
        return getCurrentDestination()
    }

    fun navigateFromBottomBar(target: NavigationTarget): NavigationTarget? {
        navController.navigate(target) {
            popUpTo(navController.graph.startDestinationId) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
        return getCurrentDestination()
    }

    fun getCurrentDestination(): NavigationTarget? {
        return navController.currentBackStackEntry?.destination
            ?.toNavigationTarget<NavigationTarget>(navigationType)
    }

    fun getPreviousDestination(): NavigationTarget? {
        return navController.previousBackStackEntry?.destination
            ?.toNavigationTarget<NavigationTarget>(navigationType)
    }
}
