package com.trackhub.feat_network.domain.cache

import com.trackhub.core_hub.domain.models.Hub
import com.trackhub.core_hub.domain.models.HubItem
import kotlinx.coroutines.flow.Flow

interface CacheDataSource {
    suspend fun addHub(hub: Hub)
    suspend fun updateOwnHubs(hubs: List<Hub>)
    suspend fun updateSharedHubs(hubs: List<Hub>)
    fun getOwnHubs(): Flow<List<Hub>>
    fun getSharedHubs(): Flow<List<Hub>>
    suspend fun addItem(item: HubItem)
    suspend fun addItems(items: List<HubItem>)
    suspend fun getItemsFromHub(hubId: String): List<HubItem>
}