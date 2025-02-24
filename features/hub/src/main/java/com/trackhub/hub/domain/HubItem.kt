package com.trackhub.hub.domain

import java.math.BigDecimal

data class HubItem(
    val id: Int,
    val hubId: Int,
    val name: String,
    val stockCount: BigDecimal,
    val unit: String,
    val imageUrl: String?,
    val createdAt: String?,
    val updatedAt: String?
)
