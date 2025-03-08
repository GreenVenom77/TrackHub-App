package com.trackhub.feat_hub.presentation.hub_details

import com.greenvenom.core_network.data.NetworkError
import com.greenvenom.core_network.data.NetworkResult
import com.trackhub.core_hub.domain.models.Hub
import com.trackhub.core_hub.domain.models.HubItem

data class HubDetailsState(
    val hub: Hub? = null,
    val updatingHubResult: NetworkResult<Unit, NetworkError>? = null,
    val deletingHubResult: NetworkResult<Unit, NetworkError>? = null,
    val addingItemResult: NetworkResult<Unit, NetworkError>? = null,
    val updatingItemResult: NetworkResult<Unit, NetworkError>? = null,
    val deletingItemResult: NetworkResult<Unit, NetworkError>? = null,
    val hubItemsResult: NetworkResult<Set<HubItem>, NetworkError>? = null
)