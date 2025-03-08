package com.trackhub.feat_hub.presentation.hub_details

interface HubDetailsAction {
    data class UpdateHub(
        val hubName: String = "",
        val hubDescription: String = ""
    ): HubDetailsAction
    data class DeleteHub(val hubId: String): HubDetailsAction
    data class AddItem(
        val itemName: String,
        val itemStock: String,
        val itemUnit: String
    ): HubDetailsAction
    data class UpdateItem(
        val itemId: Int,
        val itemName: String = "",
        val itemStock: String = "",
        val itemUnit: String = ""
    ): HubDetailsAction
    data class DeleteItem(val itemId: Int): HubDetailsAction
    data class StartCollectingHubItems(val hubId: String): HubDetailsAction
    data object StopCollectingHubItems: HubDetailsAction
    data object ClearState: HubDetailsAction
}