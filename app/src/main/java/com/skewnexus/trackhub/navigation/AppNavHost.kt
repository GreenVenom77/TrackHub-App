package com.skewnexus.trackhub.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.greenvenom.navigation.AppDestination
import com.greenvenom.navigation.AppNavigator
import com.greenvenom.navigation.SubGraph
import com.greenvenom.navigation.repository.NavigationStateRepository
import com.greenvenom.navigation.utils.appDestinationSaver
import org.koin.compose.koinInject

@Composable
fun AppNavHost(modifier: Modifier = Modifier) {
    val appNavigator = koinInject<AppNavigator>()
    val navigationStateRepository = koinInject<NavigationStateRepository>()

    val currentDestination = rememberSaveable(stateSaver = appDestinationSaver) {
        mutableStateOf(appNavigator.getCurrentDestination())
    }

    when (currentDestination.value) {
        AppDestination.Home,
        AppDestination.AIChat,
        AppDestination.Sessions,
        AppDestination.Doctors -> {
            navigationStateRepository.updateBarsState(
                bottomBarState = true,
                topBarState = true
            )
        }

        else -> {
            navigationStateRepository.updateBarsState(
                bottomBarState = false,
                topBarState = false
            )
        }
    }

    NavHost(
        navController = koinInject(),
        startDestination = SubGraph.Auth,
        modifier = modifier
    ) {
        navigation<SubGraph.Auth>(startDestination = AppDestination.Login) {
            composable<AppDestination.Login> {

            }
            composable<AppDestination.Register> {

            }
            composable<AppDestination.VerifyEmail> {

            }
            composable<AppDestination.OTP> {

            }
            composable<AppDestination.NewPassword> {

            }
        }
    }
}

//private fun handleSessionStatus(sessionStatus: SessionStatus) {
//    when(sessionStatus) {
//        SessionStatus.Initializing -> {
//
//        }
//        is SessionStatus.Authenticated -> {
//            when(sessionStatus.source) {
//                SessionSource.AnonymousSignIn -> TODO()
//                SessionSource.External -> TODO()
//                is SessionSource.Refresh -> TODO()
//                is SessionSource.SignIn -> TODO()
//                is SessionSource.SignUp -> TODO()
//                SessionSource.Storage -> TODO()
//                SessionSource.Unknown -> TODO()
//                is SessionSource.UserChanged -> TODO()
//                is SessionSource.UserIdentitiesChanged -> TODO()
//            }
//        }
//        is SessionStatus.NotAuthenticated -> {
//            if (sessionStatus.isSignOut) {
//
//            } else {
//
//            }
//        }
//        is SessionStatus.RefreshFailure -> {
//            // do something
//        }
//    }
//}