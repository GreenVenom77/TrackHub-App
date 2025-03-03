package com.trackhub.hub.presentation.hub_details

import com.greenvenom.networking.data.NetworkError
import com.greenvenom.networking.data.NetworkResult
import com.trackhub.hub.domain.models.Hub
import com.trackhub.hub.domain.models.HubItem

data class HubDetailsState(
    val hub: Hub? = null,
    val hubItemsResult: NetworkResult<List<HubItem>, NetworkError>? = null
)