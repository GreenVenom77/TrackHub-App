package com.skewnexus.trackhub.data.cache

import com.skewnexus.trackhub.domain.cache.CacheDataSource
import com.trackhub.hub.data.cache.dao.HubDao
import com.trackhub.hub.data.cache.entities.toHub
import com.trackhub.hub.data.cache.entities.toHubEntity
import com.trackhub.hub.data.cache.entities.toHubItem
import com.trackhub.hub.data.cache.entities.toHubItemEntity
import com.trackhub.hub.domain.models.Hub
import com.trackhub.hub.domain.models.HubItem

class RoomDataSource(
    private val hubDao: HubDao
): CacheDataSource {
    override suspend fun addHub(hub: Hub) {
        hubDao.addHub(hub.toHubEntity())
    }

    override suspend fun updateOwnHubs(hubs: List<Hub>) {
        hubDao.updateOwnHubs(hubs.map { it.toHubEntity() })
    }

    override suspend fun updateSharedHubs(hubs: List<Hub>) {
        hubDao.updateSharedHubs(hubs.map { it.toHubEntity(false) })
    }

    override suspend fun getOwnHubs(): List<Hub> {
        return hubDao.getOwnHubs().map { it.toHub() }
    }

    override suspend fun getSharedHubs(): List<Hub> {
        return hubDao.getSharedHubs().map { it.toHub() }
    }

    override suspend fun addItem(item: HubItem) {
        hubDao.addNewItem(item.toHubItemEntity())
    }

    override suspend fun addItems(items: List<HubItem>) {
        hubDao.addNewItems(items.map { it.toHubItemEntity() })
    }

    override suspend fun getItemsFromHub(hubId: Int): List<HubItem> {
        return hubDao.getItemsFromHub(hubId).map { it.toHubItem() }
    }
}