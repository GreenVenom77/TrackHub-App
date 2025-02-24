package com.trackhub.hub.presentation.models

import com.trackhub.domain.cache.hub.models.Hub

data class HubUI(
    val id: Int? = null,
    val userId: String,
    val name: String,
    val createdAt: String? = null,
    val updatedAt: String? = null
)

fun Hub.toHubUI(): HubUI {
    return HubUI(
        id = this.id,
        name = this.name,
        userId = this.userId,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}