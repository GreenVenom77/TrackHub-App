package com.trackhub.feat_network.data.remote.repository

import android.util.Log
import com.trackhub.feat_network.data.remote.SessionDestinations
import com.greenvenom.core_network.supabase.util.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.status.SessionSource
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SupabaseSessionRepository(supabaseClient: SupabaseClient) {
    private val client = supabaseClient.getClient()
    private val scope = CoroutineScope(Dispatchers.IO)

    private val _userSessionDestination = MutableStateFlow(SessionDestinations.INITIALIZE)
    val userSessionDestination = _userSessionDestination
        .stateIn(scope, SharingStarted.WhileSubscribed(5000), SessionDestinations.INITIALIZE)

    init {
        collectSessionStatus()
    }

    fun collectSessionStatus() {
        scope.launch {
            client.auth.sessionStatus.collect {
                handleSessionStatus(it)
            }
        }
    }

    private fun handleSessionStatus(sessionStatus: SessionStatus) {
        when(sessionStatus) {
            SessionStatus.Initializing -> {  }
            is SessionStatus.Authenticated -> {
                when(sessionStatus.source) {
                    is SessionSource.Refresh,
                    is SessionSource.Storage,
                    is SessionSource.SignIn -> {
                        _userSessionDestination.update { SessionDestinations.MAIN }
                    }
                    is SessionSource.UserChanged -> {
                        _userSessionDestination.update { SessionDestinations.AUTH }
                    }
                    else -> { Log.i("Session Source", sessionStatus.source.toString()) }
                }
            }
            is SessionStatus.NotAuthenticated -> {
                _userSessionDestination.update { SessionDestinations.AUTH }
            }
            is SessionStatus.RefreshFailure -> {
                _userSessionDestination.update { SessionDestinations.AUTH }
            }
        }
    }
}