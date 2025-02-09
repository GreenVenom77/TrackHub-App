package com.greenvenom.networking.supabase.data.repository

import android.util.Log
import com.greenvenom.networking.domain.SessionDestinations
import com.greenvenom.networking.supabase.util.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.status.SessionSource
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SessionStateRepository(supabaseClient: SupabaseClient) {
    private val client = supabaseClient.getClient()
    private val _userSessionEvents = Channel<SessionDestinations>(Channel.CONFLATED)
    val userSessionEvents = _userSessionEvents.receiveAsFlow()

    init {
        collectSessionStatus()
    }

    fun collectSessionStatus() {
        CoroutineScope(Dispatchers.IO).launch {
            client.auth.sessionStatus.collect {
                handleSessionStatus(it)
            }
        }
    }

    private suspend fun handleSessionStatus(sessionStatus: SessionStatus) {
        when(sessionStatus) {
            SessionStatus.Initializing -> {  }
            is SessionStatus.Authenticated -> {
                when(sessionStatus.source) {
                    is SessionSource.Refresh,
                    is SessionSource.Storage,
                    is SessionSource.SignIn -> {
                        _userSessionEvents.send(SessionDestinations.HOME)
                    }
                    is SessionSource.UserChanged -> {
                        _userSessionEvents.send(SessionDestinations.SIGN_IN)
                    }
                    else -> { Log.i("Session Source", sessionStatus.source.toString()) }
                }
            }
            is SessionStatus.NotAuthenticated -> {
                _userSessionEvents.send(SessionDestinations.SIGN_IN)
            }
            is SessionStatus.RefreshFailure -> {
                _userSessionEvents.send(SessionDestinations.SIGN_IN)
            }
        }
    }
}