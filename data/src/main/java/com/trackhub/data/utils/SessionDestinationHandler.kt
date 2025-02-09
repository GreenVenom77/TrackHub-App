package com.trackhub.data.utils

import com.greenvenom.navigation.data.NavigationType
import com.greenvenom.navigation.routes.SubGraph
import com.greenvenom.navigation.repository.NavigationStateRepository
import com.greenvenom.networking.supabase.data.repository.SessionStateRepository
import com.greenvenom.networking.domain.SessionDestinations
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SessionDestinationHandler(
    private val navigationStateRepository: NavigationStateRepository,
    private val sessionStateRepository: SessionStateRepository
) {
    init {
        collectSessionDestinations()
    }

    fun collectSessionDestinations() {
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