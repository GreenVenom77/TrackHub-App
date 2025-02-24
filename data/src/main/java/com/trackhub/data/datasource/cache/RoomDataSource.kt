package com.trackhub.data.datasource.cache

import com.trackhub.domain.cache.CacheDataSource
import com.trackhub.data.datasource.cache.hub.dao.HubDao
import com.trackhub.data.datasource.cache.hub.entities.toHub
import com.trackhub.data.datasource.cache.hub.entities.toHubEntity
import com.trackhub.data.datasource.cache.hub.entities.toHubItem
import com.trackhub.data.datasource.cache.hub.entities.toHubItemEntity
import com.trackhub.domain.cache.hub.models.Hub
import com.trackhub.domain.cache.hub.models.HubItem

class RoomDataSource(
    private val hubDao: HubDao
): CacheDataSource {
    override suspend fun addNewOwnHub(hub: Hub) {
        hubDao.addNewOwnHub(hub.toHubEntity())
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

    override suspend fun addNewItem(item: HubItem) {
        hubDao.addNewItem(item.toHubItemEntity())
    }

    override suspend fun addNewItems(items: List<HubItem>) {
        hubDao.addNewItems(items.map { it.toHubItemEntity() })
    }

    override suspend fun getItemsFromHub(hubId: Int): List<HubItem> {
        return hubDao.getItemsFromHub(hubId).map { it.toHubItem() }
    }
}