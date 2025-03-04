package com.skewnexus.trackhub.navigation.utils

import com.greenvenom.core_navigation.data.NavigationType
import com.greenvenom.core_navigation.data.repository.NavigationStateRepository
import com.greenvenom.feat_network.data.remote.SessionDestinations
import com.greenvenom.feat_network.data.remote.repository.SupabaseSessionRepository
import com.greenvenom.feat_navigation.routes.SubGraph
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SessionDestinationHandler(
    private val navigationStateRepository: NavigationStateRepository,
    private val sessionStateRepository: SupabaseSessionRepository
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