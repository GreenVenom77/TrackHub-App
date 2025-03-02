package com.trackhub.hub.presentation.hub_details

import com.trackhub.hub.presentation.models.HubItemUI
import com.trackhub.hub.presentation.models.HubUI

data class HubDetailsState(
    val hub: HubUI? = null,
    val hubItems: List<HubItemUI>? = null
)