package com.trackhub.hub.presentation.hub_details

import androidx.lifecycle.viewModelScope
import com.greenvenom.base.presentation.BaseViewModel
import com.trackhub.hub.domain.repository.HubRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

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

        }
    }
}