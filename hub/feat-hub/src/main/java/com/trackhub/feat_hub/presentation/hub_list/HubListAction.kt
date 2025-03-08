package com.trackhub.feat_hub.presentation.hub_list

interface HubListAction {
    data class AddHub(val hubName: String, val hubDescription: String) : HubListAction
    data class StartCollectingHubs(val isOwned: Boolean) : HubListAction
    data class StopCollectingHubs(val isOwned: Boolean) : HubListAction
}