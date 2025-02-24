package com.trackhub.data.datasource.remote.hub.dto

import com.trackhub.domain.cache.hub.models.HubItem
import kotlinx.serialization.Serializable

@Serializable
data class HubItemDto(
    val id: Int? = null,
    val hubId: Int,
    val name: String,
    val stockCount: String,
    val unit: String,
    val imageUrl: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
)

fun HubItem.toHubItemDto(): HubItemDto {
    return HubItemDto(
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

fun HubItemDto.toHubItem(): HubItem {
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