package com.skewnexus.trackhub

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.greenvenom.core_ui.theme.AppTheme
import com.greenvenom.core_navigation.data.repository.NavigationStateRepository
import com.skewnexus.trackhub.navigation.AppNavHost
import com.trackhub.feat_navigation.components.BottomNavigationBar
import com.trackhub.feat_navigation.components.TopAppBar
import com.trackhub.feat_navigation.routes.Screen
import org.koin.compose.koinInject

class MainActivity : AppCompatActivity() {
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
                            currentDestination = navigationState.currentDestination ?: Screen.MyHubs,
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