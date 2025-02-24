package com.trackhub.hub.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class HubItemDto(
    val id: Int,
    val hubId: Int,
    val name: String,
    val stockCount: String,
    val unit: String,
    val imageUrl: String?,
    val createdAt: String?,
    val updatedAt: String?
)
