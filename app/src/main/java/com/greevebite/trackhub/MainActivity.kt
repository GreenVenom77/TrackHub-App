package com.greevebite.trackhub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import com.greevebite.trackhub.ui.theme.TrackHubTheme
import com.greevebite.auth.presentation.login.screen.LoginScreen
import com.greevebite.trackhub.navigation.AppNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TrackHubTheme {
                Surface {
                    AppNavHost()
                }
            }
        }
    }
}