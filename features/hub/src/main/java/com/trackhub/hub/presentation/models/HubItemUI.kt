package com.trackhub.hub.presentation.models

data class HubItemUI(
    val id: Int,
    val hubId: Int,
    val name: String,
    val stockCount: String,
    val unit: String,
    val imageUrl: String?,
    val createdAt: String?,
    val updatedAt: String?
)
