package com.greenvenom.core_hub.data.cache.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.greenvenom.core_hub.data.cache.entities.HubEntity
import com.greenvenom.core_hub.data.cache.entities.HubItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HubDao {
    @Upsert
    suspend fun addHub(hub: HubEntity)

    @Upsert
    suspend fun updateOwnHubs(hubs: List<HubEntity>)

    @Upsert
    suspend fun updateSharedHubs(hubs: List<HubEntity>)

    @Query("SELECT * FROM hubs Where is_owned = 1")
    fun getOwnHubs(): Flow<List<HubEntity>>

    @Query("SELECT * FROM hubs Where is_owned = 0")
    fun getSharedHubs(): Flow<List<HubEntity>>

    @Upsert
    suspend fun addNewItem(item: HubItemEntity)

    @Upsert
    suspend fun addNewItems(items: List<HubItemEntity>)

    @Query("SELECT * FROM hub_items Where hub_id = :hubId")
    suspend fun getItemsFromHub(hubId: String): List<HubItemEntity>
}