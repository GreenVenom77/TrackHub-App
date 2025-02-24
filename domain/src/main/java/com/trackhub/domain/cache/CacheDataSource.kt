package com.trackhub.domain.cache

import com.trackhub.domain.cache.hub.models.Hub
import com.trackhub.domain.cache.hub.models.HubItem

interface CacheDataSource {
    suspend fun addNewOwnHub(hub: Hub)
    suspend fun updateOwnHubs(hubs: List<Hub>)
    suspend fun updateSharedHubs(hubs: List<Hub>)
    suspend fun getOwnHubs(): List<Hub>
    suspend fun getSharedHubs(): List<Hub>
    suspend fun addNewItem(item: HubItem)
    suspend fun addNewItems(items: List<HubItem>)
    suspend fun getItemsFromHub(hubId: Int): List<HubItem>
}