package com.greenvenom.auth.presentation.splash

import androidx.compose.runtime.Composable
import com.greenvenom.networking.supabase.data.repository.SessionStateRepository
import com.trackhub.data.utils.SessionDestinationHandler
import org.koin.compose.koinInject

@Composable
fun SplashScreen(

) {
    koinInject<SessionStateRepository>()
    koinInject<SessionDestinationHandler>()
}