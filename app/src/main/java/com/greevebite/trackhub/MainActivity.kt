package com.greevebite.trackhub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import com.greevebite.trackhub.core.di.networkModule
import com.greevebite.trackhub.core.theme.TrackHubTheme
import com.greevebite.trackhub.features.auth.login.screen.LoginScreen
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startKoin{
            androidLogger()
            androidContext(this@MainActivity)
            modules(networkModule)
        }
        enableEdgeToEdge()
        setContent {
            TrackHubTheme {
                Surface {
                    LoginScreen()
                }
            }
        }
    }
}