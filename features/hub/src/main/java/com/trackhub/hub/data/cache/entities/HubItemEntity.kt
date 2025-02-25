package com.trackhub.hub.data.cache.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.trackhub.hub.domain.models.HubItem

@Entity(
    tableName = "hub_items",
    foreignKeys = [
        ForeignKey(
            entity = HubEntity::class,
            parentColumns = ["id"],
            childColumns = ["hub_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("hub_id")
    ]
)
data class HubItemEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int? = null,
    @ColumnInfo(name = "hub_id")
    val hubId: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "stock_count")
    val stockCount: String,
    @ColumnInfo(name = "unit")
    val unit: String,
    @ColumnInfo(name = "image_url")
    val imageUrl: String? = null,
    @ColumnInfo(name = "created_at")
    val createdAt: String? = null,
    @ColumnInfo(name = "updated_at")
    val updatedAt: String? = null
)

fun HubItem.toHubItemEntity(): HubItemEntity {
    return HubItemEntity(
        id = this.id,
        hubId = this.hubId,
        name = this.name,
        stockCount = this.stockCount.toString(),
        unit = this.unit,
        imageUrl = this.imageUrl,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}

fun HubItemEntity.toHubItem(): HubItem {
    return HubItem(
        id = this.id,
        hubId = this.hubId,
        name = this.name,
        stockCount = this.stockCount.toBigDecimal(),
        unit = this.unit,
        imageUrl = this.imageUrl,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}