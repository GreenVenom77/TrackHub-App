package com.greenvenom.feat_hub.presentation.hub_details

import com.greenvenom.core_network.data.NetworkError
import com.greenvenom.core_network.data.NetworkResult
import com.greenvenom.core_hub.domain.models.Hub
import com.greenvenom.core_hub.domain.models.HubItem

data class HubDetailsState(
    val hub: Hub? = null,
    val hubItemsResult: NetworkResult<List<HubItem>, NetworkError>? = null
)