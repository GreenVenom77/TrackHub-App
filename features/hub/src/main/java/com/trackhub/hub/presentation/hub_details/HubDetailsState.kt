package com.trackhub.hub.presentation.hub_details

import com.greenvenom.networking.data.NetworkError
import com.greenvenom.networking.data.NetworkResult
import com.trackhub.hub.presentation.models.HubItemUI
import com.trackhub.hub.presentation.models.HubUI

data class HubDetailsState(
    val hub: HubUI? = null,
    val hubItemsResult: NetworkResult<List<HubItemUI>, NetworkError>? = null
)