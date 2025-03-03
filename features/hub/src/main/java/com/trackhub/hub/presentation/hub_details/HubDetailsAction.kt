package com.trackhub.hub.presentation.hub_details

interface HubDetailsAction {
    data class UpdateHub(val hubId: String): HubDetailsAction
    data class GetHubItems(val hubId: String): HubDetailsAction
    data object RefreshHubItems: HubDetailsAction
}