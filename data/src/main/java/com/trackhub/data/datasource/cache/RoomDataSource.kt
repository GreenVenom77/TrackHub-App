package com.trackhub.data.datasource.cache

import com.trackhub.domain.datasource.cache.CacheDataSource
import com.trackhub.hub.domain.Hub
import com.trackhub.hub.domain.HubItem

class RoomDataSource(

): CacheDataSource {
    override suspend fun addNewOwnHub(hub: Hub) {
        TODO("Not yet implemented")
    }

    override suspend fun updateOwnHubs(hubs: List<Hub>) {
        TODO("Not yet implemented")
    }

    override suspend fun updateSharedHubs(hubs: List<Hub>) {
        TODO("Not yet implemented")
    }

    override suspend fun getOwnHubs(): List<Hub> {
        TODO("Not yet implemented")
    }

    override suspend fun getSharedHubs(): List<Hub> {
        TODO("Not yet implemented")
    }

    override suspend fun addNewItem(item: HubItem) {
        TODO("Not yet implemented")
    }

    override suspend fun addNewItems(items: List<HubItem>) {
        TODO("Not yet implemented")
    }

    override suspend fun getItemsFromHub(hubId: Int): List<HubItem> {
        TODO("Not yet implemented")
    }
}