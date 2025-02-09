package com.skewnexus.trackhub.navigation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.skewnexus.trackhub.navigation.routes.Screen
import com.greenvenom.navigation.NavigationTarget
import com.greenvenom.navigation.NavigationType

@Composable
fun BottomNavigationBar(
    defaultNavigationMethod: (NavigationType) -> Unit,
    currentDestination: NavigationTarget,
    isVisible: Boolean
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        content = {
            BarContent(
                defaultNavigationMethod = defaultNavigationMethod,
                currentDestination = currentDestination
            )
        }
    )
}

@Composable
private fun BarContent(
    defaultNavigationMethod: (NavigationType) -> Unit,
    currentDestination: NavigationTarget
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
    ) {
        BottomDestination.entries.forEach { destination ->
            NavigationBarItem(
                onClick = { defaultNavigationMethod(NavigationType.BottomNavigation(destination.target)) },
                icon = {
                    Icon(
                        painter = painterResource(destination.icon),
                        contentDescription = "${destination.label} Nav Icon"
                    )
                },
                label = { Text(destination.label, style = MaterialTheme.typography.labelLarge) },
                selected = destination.target == currentDestination,
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun BottomNavigationBarContent() {
    BarContent(
        defaultNavigationMethod = {  },
        currentDestination = Screen.Home
    )
}
