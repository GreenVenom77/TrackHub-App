package com.trackhub.feat_hub.presentation.hub_details

import androidx.lifecycle.viewModelScope
import com.greenvenom.core_ui.presentation.BaseViewModel
import com.greenvenom.core_network.data.map
import com.trackhub.core_hub.domain.models.Hub
import com.trackhub.core_hub.domain.models.HubItem
import com.trackhub.core_hub.domain.repository.HubRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HubDetailsViewModel(
    private val hubRepository: HubRepository
): BaseViewModel() {
    private val _hubDetailsState = MutableStateFlow(HubDetailsState())
    val hubDetailsState = _hubDetailsState
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            HubDetailsState()
        )

    private var itemsCollectionJob: Job? = null

    fun hubDetailsAction(action: HubDetailsAction) {
        when (action) {
            is HubDetailsAction.UpdateHub -> updateHub(
                action.hubName,
                action.hubDescription
            )
            is HubDetailsAction.DeleteHub -> deleteHub(action.hubId)
            is HubDetailsAction.AddItem -> addItemToHub(
                action.itemName,
                action.itemStock,
                action.itemUnit
            )
            is HubDetailsAction.UpdateItem -> updateItem(
                action.itemId,
                action.itemName,
                action.itemStock,
                action.itemUnit
            )
            is HubDetailsAction.DeleteItem -> deleteItem(action.itemId)
            is HubDetailsAction.StartCollectingHubItems -> {
                itemsCollectionJob = getHubItems(action.hubId)
            }
            is HubDetailsAction.StopCollectingHubItems -> {
                itemsCollectionJob?.cancel()
                itemsCollectionJob = null
            }
            is HubDetailsAction.ClearState -> clearState()
        }
    }

    private fun updateHub(
        hubName: String,
        hubDescription: String
    ) {
        val updatedHub = _hubDetailsState.value.hub?.copy(
            name = hubName,
            description = hubDescription
        ) as Hub

        viewModelScope.launch {
            val updateHubResult = hubRepository.updateHub(updatedHub)
            _hubDetailsState.update {
                it.copy(
                    updatingHubResult = updateHubResult
                )
            }
        }
    }

    private fun deleteHub(hubId: String) {
        viewModelScope.launch {
            val deleteHubResult = hubRepository.deleteHub(hubId)
            _hubDetailsState.update {
                it.copy(
                    deletingHubResult = deleteHubResult
                )
            }
        }
    }

    private fun addItemToHub(
        itemName: String,
        itemStock: String,
        unit: String
    ) {
        viewModelScope.launch {
            val addItemResult = hubRepository.addItemToHub(
                HubItem(
                    hubId = _hubDetailsState.value.hub?.id ?: "",
                    name = itemName,
                    stockCount = itemStock.toBigDecimal(),
                    unit = unit
                )
            )
            _hubDetailsState.update {
                it.copy(
                    addingItemResult = addItemResult
                )
            }
        }
    }

    private fun updateItem(
        itemId: Int,
        itemName: String,
        itemStock: String,
        unit: String
    ) {
        viewModelScope.launch {
            val updateItemResult = hubRepository.updateItem(
                itemId = itemId,
                itemName = itemName,
                itemStock = itemStock.toBigDecimal(),
                unit = unit
            )
            _hubDetailsState.update {
                it.copy(
                    updatingItemResult = updateItemResult
                )
            }
        }
    }

    private fun deleteItem(itemId: Int) {
        viewModelScope.launch {
            val deleteItemResult = hubRepository.deleteHubItem(itemId)
            _hubDetailsState.update {
                it.copy(
                    deletingItemResult = deleteItemResult
                )
            }
        }
    }

    private fun getHubItems(hubID: String): Job {
        return viewModelScope.launch {
            hubRepository.getHubs().first().let { hubsResult ->
                hubsResult.map { hubs ->
                    _hubDetailsState.update {
                        it.copy(
                            hub = hubs.first { hub -> hub.id == hubID }
                        )
                    }
                }
            }

            _hubDetailsState.value.hub?.let { hub ->
                hubRepository.getItemsFromHub(hub.id).collect { itemsResult ->
                    _hubDetailsState.update {
                        it.copy(
                            hubItemsResult = itemsResult.map { items -> items.toSet() }
                        )
                    }
                }
            }
        }
    }

    private fun clearState() {
        _hubDetailsState.update {
            HubDetailsState()
        }
    }
}