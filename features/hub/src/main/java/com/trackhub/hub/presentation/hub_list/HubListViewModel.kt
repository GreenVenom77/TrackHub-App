package com.trackhub.hub.presentation.hub_list

import androidx.lifecycle.viewModelScope
import com.greenvenom.base.presentation.BaseViewModel
import com.greenvenom.networking.data.NetworkError
import com.greenvenom.networking.data.NetworkResult
import com.greenvenom.networking.data.map
import com.trackhub.hub.domain.models.Hub
import com.trackhub.hub.domain.repository.HubRepository
import com.trackhub.hub.presentation.models.toHubUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
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
            hubRepository.getHubs().onEach { result ->
                getOwnedHubs(result)
            }
        }

        viewModelScope.launch {
            hubRepository.getHubs(isOwned = false).onEach { result ->
                getSharedHubs(result)
            }
        }
    }

    fun hubListAction(action: HubListAction) {
        when (action) {

        }
    }

    private fun getOwnedHubs(hubsResult: NetworkResult<List<Hub>, NetworkError>) {
        _hubListState.update {
            it.copy(
                ownedHubs = hubsResult.map { hubs -> hubs.map { hub -> hub.toHubUI() }.toSet() }
            )
        }
    }

    private fun getSharedHubs(hubsResult: NetworkResult<List<Hub>, NetworkError>) {
        _hubListState.update {
            it.copy(
                sharedHubs = hubsResult.map { hubs -> hubs.map { hub -> hub.toHubUI() }.toSet() }
            )
        }
    }
}