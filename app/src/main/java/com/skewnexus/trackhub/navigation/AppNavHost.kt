package com.skewnexus.trackhub.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.greenvenom.auth.presentation.login.LoginScreen
import com.greenvenom.auth.presentation.otp.OtpScreen
import com.greenvenom.auth.presentation.register.RegisterScreen
import com.greenvenom.auth.presentation.splash.SplashScreen
import com.greenvenom.navigation.AppDestination
import com.greenvenom.navigation.SubGraph
import com.greenvenom.navigation.repository.NavigationStateRepository
import com.greenvenom.navigation.utils.AppNavigator
import com.greenvenom.networking.supabase.data.repository.SessionStateRepository
import com.greenvenom.networking.supabase.domain.SessionDestinations
import org.koin.compose.koinInject

@Composable
fun AppNavHost(modifier: Modifier = Modifier) {
    val appNavigator = koinInject<AppNavigator>()
    val navigationStateRepository = koinInject<NavigationStateRepository>()
    val sessionStateRepository = koinInject<SessionStateRepository>()
    val userSessionState by sessionStateRepository.userSessionState.collectAsStateWithLifecycle()

    DisposableEffect(userSessionState.wantedDestination) {
        val destination = when (userSessionState.wantedDestination) {
            SessionDestinations.SIGN_IN,
            SessionDestinations.SESSION_REFRESH_FAILURE -> {
                appNavigator.navigateTo(SubGraph.Auth)
            }
            SessionDestinations.HOME -> {
                appNavigator.navigateTo(SubGraph.Main)
            }
            else -> return@DisposableEffect onDispose {}
        }

        navigationStateRepository.updateCurrentDestination(destination)

        onDispose {}
    }

    NavHost(
        navController = appNavigator.navController,
        startDestination = AppDestination.Splash,
        modifier = modifier
    ) {
        composable<AppDestination.Splash> {
            SplashScreen()
        }

        navigation<SubGraph.Auth>(startDestination = AppDestination.Login) {
            composable<AppDestination.Login> {
                LoginScreen(
                    navigateToRegisterScreen = { appNavigator.navigateTo(AppDestination.Register) },
                    navigateToEmailVerificationScreen = { appNavigator.navigateTo(AppDestination.VerifyEmail) },
                    navigateToNextScreen = {  },
                )
            }
            composable<AppDestination.Register> {
                RegisterScreen(
                    navigateBack = { appNavigator.navigateBack() },
                    navigateToAccountVerificationScreen = { appNavigator.navigateTo(AppDestination.OTP) },
                )
            }
            composable<AppDestination.VerifyEmail> {

            }
            composable<AppDestination.OTP> {
                OtpScreen(
                    navigateBack = { appNavigator.navigateBack() },
                    navigateToNewPasswordScreen = {  }
                )
            }
            composable<AppDestination.NewPassword> {

            }
        }

        navigation<SubGraph.Main>(startDestination = AppDestination.Home) {
            composable<AppDestination.Home> {
                Text(text = "Home")
            }
        }
    }
}