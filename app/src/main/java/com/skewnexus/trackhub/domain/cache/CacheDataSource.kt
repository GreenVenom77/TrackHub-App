package com.skewnexus.trackhub.domain.cache

import com.trackhub.hub.domain.models.Hub
import com.trackhub.hub.domain.models.HubItem

interface CacheDataSource {
    suspend fun addHub(hub: Hub)
    suspend fun updateOwnHubs(hubs: List<Hub>)
    suspend fun updateSharedHubs(hubs: List<Hub>)
    suspend fun getOwnHubs(): List<Hub>
    suspend fun getSharedHubs(): List<Hub>
    suspend fun addItem(item: HubItem)
    suspend fun addItems(items: List<HubItem>)
    suspend fun getItemsFromHub(hubId: Int): List<HubItem>
}