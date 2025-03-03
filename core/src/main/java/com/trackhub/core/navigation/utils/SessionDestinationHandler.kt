package com.trackhub.core.navigation.utils

import com.greenvenom.navigation.data.NavigationType
import com.greenvenom.navigation.repository.NavigationStateRepository
import com.greenvenom.networking.data.SessionDestinations
import com.greenvenom.networking.domain.repository.SessionStateRepository
import com.trackhub.core.navigation.routes.SubGraph
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SessionDestinationHandler(
    private val navigationStateRepository: NavigationStateRepository,
    private val sessionStateRepository: SessionStateRepository
) {
    fun collectSessionDestinations() {
        CoroutineScope(Dispatchers.Main).launch {
            sessionStateRepository.userSessionDestination.collect { wantedDestination ->
                handleSessionStates(wantedDestination)
            }
        }
    }

    private fun handleSessionStates(wantedDestination: SessionDestinations) {
        when (wantedDestination) {
            SessionDestinations.INITIALIZE -> {

            }

            SessionDestinations.AUTH -> {
                navigationStateRepository.updateDestination(
                    NavigationType.ClearBackStack(SubGraph.Auth)
                )
            }

            SessionDestinations.MAIN -> {
                navigationStateRepository.updateDestination(
                    NavigationType.ClearBackStack(SubGraph.Main)
                )
            }
        }
    }
}