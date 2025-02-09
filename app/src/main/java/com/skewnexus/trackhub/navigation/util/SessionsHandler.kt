package com.skewnexus.trackhub.navigation.util

import com.greenvenom.navigation.NavigationType
import com.skewnexus.trackhub.navigation.routes.SubGraph
import com.greenvenom.navigation.repository.NavigationStateRepository
import com.greenvenom.networking.supabase.data.repository.SessionStateRepository
import com.greenvenom.networking.domain.SessionDestinations
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SessionsHandler(
    private val navigationStateRepository: NavigationStateRepository,
    private val sessionStateRepository: SessionStateRepository
) {
    init {
        collectSessionEvents()
    }

    fun collectSessionEvents() {
        CoroutineScope(Dispatchers.Main).launch {
            sessionStateRepository.userSessionEvents.collect { wantedDestination ->
                handleSessionStates(wantedDestination)
            }
        }
    }

    private fun handleSessionStates(wantedDestination: SessionDestinations) {
        when (wantedDestination) {
            SessionDestinations.SIGN_IN -> {
                navigationStateRepository.updateDestination(NavigationType.ClearBackStack(SubGraph.Auth))
            }

            SessionDestinations.HOME -> {
                navigationStateRepository.updateDestination(NavigationType.ClearBackStack(SubGraph.Main))
            }
        }
    }
}