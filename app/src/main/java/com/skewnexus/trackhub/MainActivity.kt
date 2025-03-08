package com.skewnexus.trackhub

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.greenvenom.core_navigation.data.NavigationType
import com.greenvenom.core_ui.theme.AppTheme
import com.greenvenom.core_navigation.data.repository.NavigationStateRepository
import com.skewnexus.trackhub.navigation.AppNavHost
import com.trackhub.feat_navigation.components.BottomNavigationBar
import com.trackhub.feat_navigation.components.TopAppBar
import com.trackhub.feat_navigation.data.DestinationHandler
import com.trackhub.feat_navigation.routes.Screen
import kotlinx.coroutines.flow.update
import org.koin.compose.koinInject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navigationRepository = koinInject<NavigationStateRepository>()
            val navigationState by navigationRepository.navigationState.collectAsStateWithLifecycle()
            val destinationHandler = koinInject<DestinationHandler>()
            val destinationState by destinationHandler.destinationState.collectAsStateWithLifecycle()

            AppTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = destinationState.currentHub?.name ?: stringResource(R.string.app_name),
                            isVisible = navigationState.topBarState,
                            isSideDestination = navigationState.isCurrentDestinationSide,
                            navigateBack = {
                                navigationRepository.navigate(NavigationType.Back)
                                destinationHandler.destinationState.update {
                                    it.copy(
                                        currentHub = null
                                    )
                                }
                            },
                            isActionEnabled = navigationState.currentDestination is Screen.HubItems,
                            action = {
                                if (navigationState.currentDestination is Screen.HubItems) {
                                    IconButton(
                                        onClick = {
                                            destinationHandler.destinationState.update {
                                                it.copy(
                                                    bottomSheetState = true
                                                )
                                            }
                                        },
                                        modifier = Modifier
                                            .size(48.dp)
                                            .padding(8.dp)
                                    ) {
                                        Icon(
                                            painter = painterResource(R.drawable.edit_ic),
                                            contentDescription = stringResource(R.string.edit_hub_Details),
                                            modifier = Modifier.size(48.dp)
                                        )
                                    }
                                }
                            }
                        )
                    },
                    bottomBar = {
                        BottomNavigationBar(
                            defaultNavigationMethod = navigationRepository::navigate,
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