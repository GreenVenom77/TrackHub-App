package com.trackhub.feat_hub.presentation.hub_list

import androidx.lifecycle.viewModelScope
import com.greenvenom.core_ui.presentation.BaseViewModel
import com.trackhub.core_hub.domain.models.Hub
import com.trackhub.core_hub.domain.repository.HubRepository
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
            hubRepository.getHubs().collect { ownedHubsResult ->
                _hubListState.update {
                    it.copy(
                        ownedHubsResult = ownedHubsResult
                    )
                }
            }
        }

        viewModelScope.launch {
            hubRepository.getHubs(isOwned = false).collect { sharedHubsResult ->
                _hubListState.update {
                    it.copy(
                        sharedHubsResult = sharedHubsResult
                    )
                }
            }
        }
    }

    fun hubListAction(action: HubListAction) {
        when (action) {
            is HubListAction.AddHub -> addHub(action.hubName, action.hubDescription)
        }
    }

    private fun addHub(hubName: String, hubDescription: String) {
        viewModelScope.launch {
            _hubListState.update {
                it.copy(
                    addHubResult = hubRepository.addHub(
                        Hub(
                            name = hubName,
                            description = hubDescription
                        )
                    )
                )
            }
        }
    }
}