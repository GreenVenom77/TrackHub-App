package com.skewnexus.trackhub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.greenvenom.navigation.AppDestination
import com.greenvenom.navigation.utils.AppNavigator
import com.greenvenom.navigation.presentation.BottomNavigationBar
import com.greenvenom.navigation.repository.NavigationStateRepository
import com.greenvenom.navigation.utils.NavHostControllerHolder
import com.greenvenom.ui.components.TopAppBar
import com.greenvenom.ui.theme.AppTheme
import com.skewnexus.trackhub.navigation.AppNavHost
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.activityScope
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf
import org.koin.core.scope.Scope

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navHostControllerHolder = koinInject<NavHostControllerHolder>()
            navHostControllerHolder.setNavController(rememberNavController())

            val appNavigator = koinInject<AppNavigator>()
            val navigationRepository = koinInject<NavigationStateRepository>()
            val navigationState by navigationRepository.navigationState.collectAsStateWithLifecycle()

            AppTheme {
                Scaffold(
                    topBar = { TopAppBar(isVisible = navigationState.topBarState) },
                    bottomBar = {
                        BottomNavigationBar(
                            navigateToHome = { appNavigator.navigateAndClearBackStack(AppDestination.Home) },
                            navigateToSessions = { appNavigator.navigateTo(AppDestination.Sessions) },
                            navigateToDoctors = { appNavigator.navigateTo(AppDestination.Doctors) },
                            navigateToAIChat = { appNavigator.navigateTo(AppDestination.AIChat) },
                            currentDestination = navigationState.currentDestination,
                            isVisible = navigationState.bottomBarState
                        )
                    }
                ) { innerPadding ->
                    AppNavHost(modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                    )
                }
            }
        }
    }
}