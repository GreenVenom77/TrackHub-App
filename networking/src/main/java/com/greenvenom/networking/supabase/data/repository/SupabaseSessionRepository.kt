package com.greenvenom.networking.supabase.data.repository

import android.util.Log
import com.greenvenom.networking.data.SessionDestinations
import com.greenvenom.networking.domain.repository.SessionStateRepository
import com.greenvenom.networking.supabase.util.SupabaseClient
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

class SupabaseSessionRepository(supabaseClient: SupabaseClient): SessionStateRepository {
    private val client = supabaseClient.getClient()
    private val scope = CoroutineScope(Dispatchers.IO)

    private val _userSessionDestination = MutableStateFlow(SessionDestinations.INITIALIZE)
    override val userSessionDestination = _userSessionDestination
        .stateIn(scope, SharingStarted.WhileSubscribed(5000), SessionDestinations.INITIALIZE)

    init {
        collectSessionStatus()
    }

    override fun collectSessionStatus() {
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