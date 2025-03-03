package com.trackhub.hub.presentation.hub_list

import com.greenvenom.networking.data.NetworkError
import com.greenvenom.networking.data.NetworkResult
import com.trackhub.hub.domain.models.Hub

data class HubListState(
    val ownedHubsResult: NetworkResult<List<Hub>, NetworkError>? = null,
    val sharedHubsResult: NetworkResult<List<Hub>, NetworkError>? = null
)