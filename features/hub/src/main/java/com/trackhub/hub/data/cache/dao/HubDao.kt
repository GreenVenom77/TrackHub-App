package com.trackhub.hub.data.cache.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.trackhub.hub.data.cache.entities.HubEntity
import com.trackhub.hub.data.cache.entities.HubItemEntity

@Dao
interface HubDao {
    @Upsert
    suspend fun addHub(hub: HubEntity)

    @Upsert
    suspend fun updateOwnHubs(hubs: List<HubEntity>)

    @Upsert
    suspend fun updateSharedHubs(hubs: List<HubEntity>)

    @Query("SELECT * FROM hubs Where is_owned = 1")
    suspend fun getOwnHubs(): List<HubEntity>

    @Query("SELECT * FROM hubs Where is_owned = 0")
    suspend fun getSharedHubs(): List<HubEntity>

    @Upsert
    suspend fun addNewItem(item: HubItemEntity)

    @Upsert
    suspend fun addNewItems(items: List<HubItemEntity>)

    @Query("SELECT * FROM hub_items Where hub_id = :hubId")
    suspend fun getItemsFromHub(hubId: Int): List<HubItemEntity>
}