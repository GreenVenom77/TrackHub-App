package com.trackhub.domain.datasource.cache

import com.trackhub.hub.domain.Hub
import com.trackhub.hub.domain.HubItem

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