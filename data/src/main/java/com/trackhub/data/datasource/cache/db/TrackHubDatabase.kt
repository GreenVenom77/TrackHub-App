package com.trackhub.data.datasource.cache.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.trackhub.data.datasource.cache.hub.dao.HubDao
import com.trackhub.data.datasource.cache.hub.entities.HubEntity
import com.trackhub.data.datasource.cache.hub.entities.HubItemEntity

@Database(
    entities = [HubEntity::class, HubItemEntity::class],
    version = 1
)
abstract class TrackHubDatabase: RoomDatabase() {
    abstract val hubDao: HubDao
}