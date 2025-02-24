package com.trackhub.hub.domain.mappers

import com.trackhub.hub.data.remote.dto.HubDto
import com.trackhub.hub.data.remote.dto.HubItemDto
import com.trackhub.hub.domain.Hub
import com.trackhub.hub.domain.HubItem
import com.trackhub.hub.presentation.models.HubItemUI
import com.trackhub.hub.presentation.models.HubUI

fun Hub.toHubDto(): HubDto {
    return HubDto(
        id = this.id,
        name = this.name,
        userId = this.userId,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}

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

fun Hub.toHubUI(): HubUI {
    return HubUI(
        id = this.id,
        name = this.name,
        userId = this.userId,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}

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