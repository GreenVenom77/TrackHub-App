package com.skewnexus.trackhub.navigation

import com.greenvenom.navigation.SubGraph
import com.greenvenom.navigation.repository.NavigationStateRepository
import com.greenvenom.networking.supabase.data.repository.SessionStateRepository
import com.greenvenom.networking.supabase.domain.SessionDestinations
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class SessionsHandler(
    private val navigationStateRepository: NavigationStateRepository,
    private val sessionStateRepository: SessionStateRepository
) {
    init {
        CoroutineScope(Dispatchers.Main).launch {
            sessionStateRepository.userSessionEvents.collect { wantedDestination ->
                handleSessionStates(wantedDestination)
            }
        }
    }

    private fun handleSessionStates(wantedDestination: SessionDestinations) {
        when (wantedDestination) {
            SessionDestinations.SIGN_IN -> {
                navigationStateRepository.updateCurrentDestination(
                    isClearingBackStack = true,
                    wantedDestination = SubGraph.Auth
                )
            }

            SessionDestinations.HOME -> {
                navigationStateRepository.updateCurrentDestination(
                    isClearingBackStack = true,
                    wantedDestination = SubGraph.Main
                )
            }
        }
    }
}