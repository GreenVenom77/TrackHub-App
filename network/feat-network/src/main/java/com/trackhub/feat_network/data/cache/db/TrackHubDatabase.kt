package com.trackhub.feat_network.data.cache.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.trackhub.core_hub.data.cache.dao.HubDao
import com.trackhub.core_hub.data.cache.entities.HubEntity
import com.trackhub.core_hub.data.cache.entities.HubItemEntity

@Database(
    entities = [HubEntity::class, HubItemEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TrackHubDatabase: RoomDatabase() {
    abstract val hubDao: HubDao
}