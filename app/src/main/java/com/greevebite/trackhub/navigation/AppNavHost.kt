package com.greevebite.trackhub.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.greevebite.auth.presentation.login.screen.LoginScreen
import com.greevebite.auth.presentation.otp.screen.OtpScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val appNavigator = remember(navController) {
        AppNavigator(navController)
    }

    NavHost(
        navController = navController,
        startDestination = AppDestination.Login
    ) {
        composable<AppDestination.Login> {
            LoginScreen { appNavigator.navigateAndClearBackStack(AppDestination.Home) }
        }
        composable<AppDestination.Register> {

        }
        composable<AppDestination.VerifyEmail> {

        }
        composable<AppDestination.OTP> {
            OtpScreen { appNavigator.navigateAndClearBackStack(AppDestination.ResetPassword) }
        }
        composable<AppDestination.ResetPassword> {

        }
        composable<AppDestination.Home> {

        }
    }
}