package com.trackhub.data.datasource.cache.hub.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.trackhub.domain.cache.hub.models.Hub

@Entity(tableName = "hubs")
data class HubEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int? = null,
    @ColumnInfo(name = "user_id")
    val userId: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "is_owner")
    val isOwner: Boolean = true,
    @ColumnInfo(name = "created_at")
    val createdAt: String? = null,
    @ColumnInfo(name = "updated_at")
    val updatedAt: String? = null
)

fun Hub.toHubEntity(isOwner: Boolean = true): HubEntity {
    return HubEntity(
        id = this.id,
        userId = this.userId,
        name = this.name,
        isOwner = isOwner,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}

fun HubEntity.toHub(): Hub {
    return Hub(
        id = this.id,
        userId = this.userId,
        name = this.name,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}