package com.greenvenom.networking.supabase.data.repository

import android.util.Log
import com.greenvenom.networking.supabase.domain.SessionDestinations
import com.greenvenom.networking.supabase.util.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.status.SessionSource
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SessionStateRepository(supabaseClient: SupabaseClient) {
    private val client = supabaseClient.getClient()
    private val _userSessionState = MutableStateFlow(UserSessionState())
    val userSessionState = _userSessionState.asStateFlow()

    init {
        collectSessionStatus()
    }

    private fun collectSessionStatus() {
        CoroutineScope(Dispatchers.IO).launch {
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
                        _userSessionState.update {
                            it.copy(
                                wantedDestination = SessionDestinations.HOME
                            )
                        }
                    }
                    is SessionSource.UserChanged -> {
                        _userSessionState.update {
                            it.copy(
                                wantedDestination = SessionDestinations.SIGN_IN
                            )
                        }
                    }
                    else -> { Log.i("Session Source", sessionStatus.source.toString()) }
                }
            }
            is SessionStatus.NotAuthenticated -> {
                _userSessionState.update {
                    it.copy(
                        wantedDestination = SessionDestinations.SIGN_IN
                    )
                }
            }
            is SessionStatus.RefreshFailure -> {
                _userSessionState.update {
                    it.copy(
                        wantedDestination = SessionDestinations.SESSION_REFRESH_FAILURE
                    )
                }
            }
        }
    }
}