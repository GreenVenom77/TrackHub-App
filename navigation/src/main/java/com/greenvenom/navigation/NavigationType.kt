package com.greenvenom.navigation

sealed class NavigationType {
    data object Back : NavigationType()
    data class ClearBackStack(val destination: NavigationTarget) : NavigationType()
    data class BottomNavigation(val destination: NavigationTarget) : NavigationType()
    data class Standard(val destination: NavigationTarget) : NavigationType()
}
