package com.greenvenom.feat_network.data.cache

import com.greenvenom.feat_network.domain.cache.CacheDataSource
import com.greenvenom.core_hub.data.cache.dao.HubDao
import com.greenvenom.core_hub.data.cache.entities.toHub
import com.greenvenom.core_hub.data.cache.entities.toHubEntity
import com.greenvenom.core_hub.data.cache.entities.toHubItem
import com.greenvenom.core_hub.data.cache.entities.toHubItemEntity
import com.greenvenom.core_hub.domain.models.Hub
import com.greenvenom.core_hub.domain.models.HubItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

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

    override fun getOwnHubs(): Flow<List<Hub>> {
        return hubDao.getOwnHubs().map { it.map { hubEntity -> hubEntity.toHub() } }
    }

    override fun getSharedHubs(): Flow<List<Hub>> {
        return hubDao.getSharedHubs().map { it.map { hubEntity -> hubEntity.toHub() } }
    }

    override suspend fun addItem(item: HubItem) {
        hubDao.addNewItem(item.toHubItemEntity())
    }

    override suspend fun addItems(items: List<HubItem>) {
        hubDao.addNewItems(items.map { it.toHubItemEntity() })
    }

    override suspend fun getItemsFromHub(hubId: String): List<HubItem> {
        return hubDao.getItemsFromHub(hubId).map { it.toHubItem() }
    }
}