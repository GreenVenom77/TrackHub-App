package com.trackhub.core_hub.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class HubItem(
    val id: Int = 0,
    val hubId: String,
    val name: String,
    val stockCount: Float,
    val unit: String,
    val imageUrl: String? = null,
    val createdAt: String = "",
    val updatedAt: String? = null
)