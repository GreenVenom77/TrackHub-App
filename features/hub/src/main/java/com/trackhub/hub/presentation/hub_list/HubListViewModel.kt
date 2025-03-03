package com.trackhub.hub.presentation.hub_list

import androidx.lifecycle.viewModelScope
import com.greenvenom.base.presentation.BaseViewModel
import com.greenvenom.networking.data.map
import com.trackhub.hub.domain.models.Hub
import com.trackhub.hub.domain.repository.HubRepository
import com.trackhub.hub.presentation.models.toHubUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HubListViewModel(
    private val hubRepository: HubRepository
): BaseViewModel() {
    private val _hubListState: MutableStateFlow<HubListState> = MutableStateFlow(HubListState())
    val hubListState = _hubListState
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            HubListState()
        )

    init {
        viewModelScope.launch {
            hubRepository.getHubs().onEach { ownedHubsResult ->
                _hubListState.update {
                    it.copy(
                        ownedHubsResult = ownedHubsResult.map { hubs ->
                            hubs.map { hub -> hub.toHubUI() }
                        }
                    )
                }
            }
        }

        viewModelScope.launch {
            hubRepository.getHubs(isOwned = false).onEach { sharedHubsResult ->
                _hubListState.update {
                    it.copy(
                        sharedHubsResult = sharedHubsResult.map { hubs ->
                            hubs.map { hub -> hub.toHubUI() }
                        }
                    )
                }
            }
        }
    }

    fun hubListAction(action: HubListAction) {
        when (action) {

        }
    }

    private fun addHub(hub: Hub) {
        viewModelScope.launch {
            hubRepository.addHub(hub)
        }
    }
}