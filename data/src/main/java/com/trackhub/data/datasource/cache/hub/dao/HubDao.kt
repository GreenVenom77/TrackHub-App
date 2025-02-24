package com.trackhub.data.datasource.cache.hub.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.trackhub.data.datasource.cache.hub.entities.HubEntity
import com.trackhub.data.datasource.cache.hub.entities.HubItemEntity

@Dao
interface HubDao {
    @Upsert
    suspend fun addNewOwnHub(hub: HubEntity)

    @Upsert
    suspend fun updateOwnHubs(hubs: List<HubEntity>)

    @Upsert
    suspend fun updateSharedHubs(hubs: List<HubEntity>)

    @Query("SELECT * FROM hubs Where is_owner = 1")
    suspend fun getOwnHubs(): List<HubEntity>

    @Query("SELECT * FROM hubs Where is_owner = 0")
    suspend fun getSharedHubs(): List<HubEntity>

    @Upsert
    suspend fun addNewItem(item: HubItemEntity)

    @Upsert
    suspend fun addNewItems(items: List<HubItemEntity>)

    @Query("SELECT * FROM hub_items Where hub_id = :hubId")
    suspend fun getItemsFromHub(hubId: Int): List<HubItemEntity>
}