package com.greenvenom.core_hub.data.remote.dto

import com.greenvenom.core_hub.domain.models.HubItem
import kotlinx.serialization.Serializable

@Serializable
data class HubItemDto(
    val id: Int = 0,
    val hubId: String,
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