package com.greenvenom.navigation.utils

import androidx.navigation.NavHostController

class NavHostControllerHolder {
    private var _navController: NavHostController? = null
    val navController: NavHostController
        get() = checkNotNull(_navController) { "NavHostController not initialized!" }

    fun setNavController(navController: NavHostController) {
        _navController = navController
    }
}