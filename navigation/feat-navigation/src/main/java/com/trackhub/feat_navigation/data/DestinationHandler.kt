package com.trackhub.feat_navigation.data

import com.greenvenom.core_navigation.data.NavigationType
import com.greenvenom.core_navigation.data.repository.NavigationStateRepository
import com.greenvenom.core_network.domain.SessionDestinations
import com.greenvenom.core_network.domain.SessionRepository
import com.trackhub.core_hub.domain.models.Hub
import com.trackhub.feat_navigation.routes.SubGraph
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DestinationHandler(
    private val navigationStateRepository: NavigationStateRepository,
    private val sessionStateRepository: SessionRepository
) {
    val destinationState = MutableStateFlow(DestinationState())

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
                navigationStateRepository.navigate(
                    NavigationType.ClearBackStack(SubGraph.Auth)
                )
            }

            SessionDestinations.MAIN -> {
                navigationStateRepository.navigate(
                    NavigationType.ClearBackStack(SubGraph.Main)
                )
            }
        }
    }
}