package com.trackhub.hub.presentation.hub_list

import com.greenvenom.networking.data.NetworkError
import com.greenvenom.networking.data.NetworkResult
import com.trackhub.hub.presentation.models.HubUI

data class HubListState(
    val ownedHubs: NetworkResult<Set<HubUI>, NetworkError>? = null,
    val sharedHubs: NetworkResult<Set<HubUI>, NetworkError>? = null
)