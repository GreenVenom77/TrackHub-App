package com.trackhub.feat_navigation.data

import com.trackhub.core_hub.domain.models.Hub
import com.trackhub.core_hub.domain.models.HubItem

data class DestinationState(
    val currentHub: Hub? = null,
    val hubBottomSheetState: Boolean = false,
    val itemBottomSheetState: Boolean = false
)
