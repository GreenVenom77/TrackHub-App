package com.trackhub.feat_hub.presentation.hub_details

import com.greenvenom.core_network.data.NetworkError
import com.greenvenom.core_network.data.NetworkResult
import com.trackhub.core_hub.domain.models.Hub
import com.trackhub.core_hub.domain.models.HubItem

data class HubDetailsState(
    val hub: Hub? = null,
    val currentItem: HubItem? = null,
    val hubDeletionResult: NetworkResult<Unit, NetworkError>? = null,
    val hubUpdateResult: NetworkResult<Unit, NetworkError>? = null,
    val operationResult: NetworkResult<Unit, NetworkError>? = null,
    val itemDeletionResult: NetworkResult<Unit, NetworkError>? = null,
    val hubItemsResult: NetworkResult<List<HubItem>, NetworkError>? = null
)