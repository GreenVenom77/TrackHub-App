package com.skewnexus.trackhub.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.greenvenom.auth.presentation.login.LoginScreen
import com.greenvenom.auth.presentation.otp.OtpScreen
import com.greenvenom.auth.presentation.register.RegisterScreen
import com.greenvenom.auth.presentation.reset_password.screens.NewPasswordScreen
import com.greenvenom.auth.presentation.reset_password.screens.VerifyEmailScreen
import com.greenvenom.auth.presentation.splash.SplashScreen
import com.greenvenom.navigation.data.NavigationType
import com.greenvenom.navigation.repository.NavigationStateRepository
import com.greenvenom.navigation.utils.AppNavigator
import com.skewnexus.trackhub.navigation.routes.Screen
import com.skewnexus.trackhub.navigation.routes.SubGraph
import com.skewnexus.trackhub.navigation.utils.SessionDestinationHandler
import org.koin.compose.koinInject

@Composable
fun AppNavHost(modifier: Modifier = Modifier) {
    val appNavigator = koinInject<AppNavigator>()
    val navigationStateRepository = koinInject<NavigationStateRepository>()
    val sessionDestinationHandler = koinInject<SessionDestinationHandler>()
    val navigationState by navigationStateRepository.navigationState.collectAsStateWithLifecycle()

    appNavigator.config(
        navigationType = Screen::class,
        navController = rememberNavController()
    )
    navigationStateRepository.config(
        enableBarsDestinations = setOf(
            Screen.MyHubs,
            Screen.Activity,
            Screen.SharedHubs
        )
    )

    NavHost(
        navController = appNavigator.navController,
        startDestination = Screen.Splash,
        modifier = modifier
    ) {
        composable<Screen.Splash> {
            SplashScreen(
                onStart = {
                    sessionDestinationHandler.collectSessionDestinations()
                }
            )
        }

        navigation<SubGraph.Auth>(startDestination = Screen.Login) {
            composable<Screen.Login> {
                LoginScreen(
                    navigateToRegisterScreen = {
                        navigationStateRepository.updateDestination(NavigationType.Standard(Screen.Register))
                    },
                    navigateToEmailVerificationScreen = {
                        navigationStateRepository.updateDestination(NavigationType.Standard(Screen.VerifyEmail))
                    },
                    navigateToNextScreen = {  },
                )
            }
            composable<Screen.Register> {
                RegisterScreen(
                    navigateBack = {
                        navigationStateRepository.updateDestination(NavigationType.Back)
                    },
                    navigateToAccountVerificationScreen = {
                        navigationStateRepository.updateDestination(NavigationType.Standard(Screen.OTP))
                    }
                )
            }
            composable<Screen.VerifyEmail> {
                VerifyEmailScreen(
                    navigateBack = {
                        navigationStateRepository.updateDestination(NavigationType.Back)
                    },
                    navigateToOtpScreen = {
                        navigationStateRepository.updateDestination(NavigationType.Standard(Screen.OTP))
                    }
                )
            }
            composable<Screen.OTP> {
                OtpScreen(
                    navigateBack = {
                        navigationStateRepository.updateDestination(NavigationType.Back)
                    },
                    navigateToNewPasswordScreen = {
                        if (navigationState.previousDestination is Screen.VerifyEmail) {
                            navigationStateRepository.updateDestination(NavigationType.ClearBackStack(
                                Screen.NewPassword))
                        }
                    }
                )
            }
            composable<Screen.NewPassword> {
                NewPasswordScreen(
                    navigateBack = {
                        navigationStateRepository.updateDestination(NavigationType.Back)
                    },
                    navigateToLoginScreen = {
                        navigationStateRepository.updateDestination(NavigationType.ClearBackStack(
                            Screen.Login))
                    }
                )
            }
        }

        navigation<SubGraph.Main>(startDestination = Screen.MyHubs) {
            composable<Screen.MyHubs> {
                Text(text = "Home")
            }
            composable<Screen.SharedHubs> {
                Text(text = "Shared")
            }
            composable<Screen.Activity> {
                Text(text = "Activity")
            }
            composable<Screen.Profile> {
                Text(text = "Profile")
            }
        }
    }
}