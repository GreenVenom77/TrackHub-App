package com.trackhub.hub.presentation.hub_details

import androidx.lifecycle.viewModelScope
import com.greenvenom.base.presentation.BaseViewModel
import com.greenvenom.networking.data.map
import com.trackhub.hub.domain.repository.HubRepository
import com.trackhub.hub.presentation.models.toHubItemUI
import com.trackhub.hub.presentation.models.toHubUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HubDetailsViewModel(
    private val hubRepository: HubRepository
): BaseViewModel() {
    private val _hubDetailsState: MutableStateFlow<HubDetailsState> = MutableStateFlow(HubDetailsState())
    val hubDetailsState = _hubDetailsState
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            HubDetailsState()
        )

    fun hubDetailsAction(action: HubDetailsAction) {
        when (action) {
            is HubDetailsAction.UpdateHub -> updateHub(action.hubId)
            is HubDetailsAction.GetHubItems -> getHubItems()
            is HubDetailsAction.RefreshHubItems -> _hubDetailsState.value.hub?.let {
                hubRepository.refreshItems(it.id)
            }
        }
    }

    private fun updateHub(hubId: String) {
        viewModelScope.launch {
            hubRepository.getHubs().first().let { hubsResult ->
                hubsResult.map { hubs ->
                    _hubDetailsState.update {
                        it.copy(
                            hub = hubs.first { hub -> hub.id == hubId }.toHubUI()
                        )
                    }
                }
            }
        }
    }

    private fun getHubItems() {
        viewModelScope.launch {
            _hubDetailsState.value.hub?.let { hubUI ->
                hubRepository.getItemsFromHub(hubUI.id).onEach { itemsResult ->
                    _hubDetailsState.update {
                        it.copy(
                            hubItemsResult = itemsResult.map { hubItems ->
                                hubItems.map { hubItem -> hubItem.toHubItemUI() }
                            }
                        )
                    }
                }
            }
        }
    }
}