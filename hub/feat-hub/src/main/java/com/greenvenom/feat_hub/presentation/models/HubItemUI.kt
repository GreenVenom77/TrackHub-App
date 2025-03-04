package com.greenvenom.feat_hub.presentation.models

import com.greenvenom.core_hub.domain.models.HubItem

data class HubItemUI(
    val id: Int = 0,
    val hubId: String,
    val name: String,
    val stockCount: String,
    val unit: String,
    val imageUrl: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
)

fun HubItem.toHubItemUI(): HubItemUI {
    return HubItemUI(
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