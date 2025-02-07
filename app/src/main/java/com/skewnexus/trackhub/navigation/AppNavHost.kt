package com.skewnexus.trackhub.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.greenvenom.navigation.AppDestination
import com.greenvenom.navigation.SubGraph
import com.greenvenom.navigation.repository.NavigationStateRepository
import com.greenvenom.navigation.utils.AppNavigator
import com.greenvenom.navigation.utils.appDestinationSaver
import com.greenvenom.networking.supabase.data.repository.SessionStateRepository
import com.greenvenom.networking.supabase.domain.SessionDestinations
import org.koin.compose.koinInject

@Composable
fun AppNavHost(modifier: Modifier = Modifier) {
    val appNavigator = koinInject<AppNavigator>()
    val navigationStateRepository = koinInject<NavigationStateRepository>()
    val navigationState by navigationStateRepository.navigationState.collectAsStateWithLifecycle()

    appNavigator.addNavController(rememberNavController())
    koinInject<SessionStateRepository>()
    koinInject<SessionsHandler>()

    NavHost(
        navController = appNavigator.navController,
        startDestination = SubGraph.Auth,
        modifier = modifier
    ) {
        composable<AppDestination.Splash> {
            SplashScreen()
        }

        navigation<SubGraph.Auth>(startDestination = AppDestination.Login) {
            composable<AppDestination.Login> {
                LoginScreen(
                    navigateToRegisterScreen = {
                        navigationStateRepository.updateCurrentDestination(
                            wantedDestination = AppDestination.Register
                        )
                    },
                    navigateToEmailVerificationScreen = {
                        navigationStateRepository.updateCurrentDestination(
                            wantedDestination = AppDestination.VerifyEmail
                        )
                    },
                    navigateToNextScreen = {  },
                )
            }
            composable<AppDestination.Register> {
                RegisterScreen(
                    navigateBack = {
                        navigationStateRepository.updateCurrentDestination(isNavigatingBack = true)
                    },
                    navigateToAccountVerificationScreen = {
                        navigationStateRepository.updateCurrentDestination(
                            wantedDestination = AppDestination.OTP
                        )
                    }
                )
            }
            composable<AppDestination.VerifyEmail> {
                VerifyEmailScreen(
                    navigateBack = {
                        navigationStateRepository.updateCurrentDestination(isNavigatingBack = true)
                    },
                    navigateToOtpScreen = {
                        navigationStateRepository.updateCurrentDestination(
                            wantedDestination = AppDestination.OTP
                        )
                    }
                )
            }
            composable<AppDestination.OTP> {
                OtpScreen(
                    navigateBack = {
                        navigationStateRepository.updateCurrentDestination(isNavigatingBack = true)
                    },
                    navigateToNewPasswordScreen = {
                        if (navigationState.previousDestination is AppDestination.VerifyEmail) {
                            navigationStateRepository.updateCurrentDestination(
                                isClearingBackStack = true,
                                wantedDestination = AppDestination.NewPassword
                            )
                        }
                    }
                )
            }
            composable<AppDestination.NewPassword> {
                NewPasswordScreen(
                    navigateBack = {
                        navigationStateRepository.updateCurrentDestination(isNavigatingBack = true)
                    },
                    navigateToLoginScreen = {
                        navigationStateRepository.updateCurrentDestination(
                            isClearingBackStack = true,
                            wantedDestination = AppDestination.Login
                        )
                    }
                )
            }
        }

        navigation<SubGraph.Main>(startDestination = AppDestination.Home) {
            composable<AppDestination.Home> {
                Text(text = "Home")
            }
        }
    }
}