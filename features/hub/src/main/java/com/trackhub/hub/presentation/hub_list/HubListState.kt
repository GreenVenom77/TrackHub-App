package com.trackhub.hub.presentation.hub_list

import com.greenvenom.networking.data.NetworkError
import com.greenvenom.networking.data.NetworkResult
import com.trackhub.hub.presentation.models.HubUI

data class HubListState(
    val ownedHubsResult: NetworkResult<List<HubUI>, NetworkError>? = null,
    val sharedHubsResult: NetworkResult<List<HubUI>, NetworkError>? = null
)