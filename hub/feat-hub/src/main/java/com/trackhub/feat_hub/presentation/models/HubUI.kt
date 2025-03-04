package com.trackhub.feat_hub.presentation.models

import com.trackhub.core_hub.domain.models.Hub

data class HubUI(
    val id: String = "",
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