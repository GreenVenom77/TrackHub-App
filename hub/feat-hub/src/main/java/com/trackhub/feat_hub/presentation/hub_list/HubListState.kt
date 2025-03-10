package com.trackhub.feat_hub.presentation.hub_list

import com.greenvenom.core_network.data.NetworkError
import com.greenvenom.core_network.data.NetworkResult
import com.trackhub.core_hub.domain.models.Hub

data class HubListState(
    val ownedHubsResult: NetworkResult<List<Hub>, NetworkError>? = null,
    val sharedHubsResult: NetworkResult<List<Hub>, NetworkError>? = null,
    val addHubResult: NetworkResult<Unit, NetworkError>? = null
)