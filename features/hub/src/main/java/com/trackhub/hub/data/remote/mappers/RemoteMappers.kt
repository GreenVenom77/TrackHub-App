package com.trackhub.hub.data.remote.mappers

import com.trackhub.hub.data.remote.dto.HubDto
import com.trackhub.hub.data.remote.dto.HubItemDto
import com.trackhub.hub.domain.Hub
import com.trackhub.hub.domain.HubItem

fun HubDto.toHub(): Hub {
    return Hub(
        id = this.id,
        userId = this.userId,
        name = this.name,
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