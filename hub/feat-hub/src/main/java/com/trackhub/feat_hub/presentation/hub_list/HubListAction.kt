package com.trackhub.feat_hub.presentation.hub_list

interface HubListAction {
    data class AddHub(val hubName: String, val hubDescription: String) : HubListAction
}