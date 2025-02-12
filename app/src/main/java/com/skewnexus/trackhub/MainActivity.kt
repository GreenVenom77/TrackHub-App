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
import com.greenvenom.navigation.routes.Screen
import com.skewnexus.trackhub.navigation.components.BottomNavigationBar
import com.greenvenom.navigation.repository.NavigationStateRepository
import com.skewnexus.trackhub.navigation.components.TopAppBar
import com.greenvenom.ui.theme.AppTheme
import com.skewnexus.trackhub.navigation.AppNavHost
import org.koin.compose.koinInject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navigationRepository = koinInject<NavigationStateRepository>()
            val navigationState by navigationRepository.navigationState.collectAsStateWithLifecycle()

            AppTheme {
                Scaffold(
                    topBar = { TopAppBar(isVisible = navigationState.topBarState) },
                    bottomBar = {
                        BottomNavigationBar(
                            defaultNavigationMethod = navigationRepository::updateDestination,
                            currentDestination = navigationState.currentDestination ?: Screen.Home,
                            isVisible = navigationState.bottomBarState
                        )
                    }
                ) { innerPadding ->
                    AppNavHost(
                        modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                    )
                }
            }
        }
    }
}