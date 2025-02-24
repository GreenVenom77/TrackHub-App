package com.trackhub.domain.cache.hub.models

import java.math.BigDecimal

data class HubItem(
    val id: Int? = null,
    val hubId: Int,
    val name: String,
    val stockCount: BigDecimal,
    val unit: String,
    val imageUrl: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
)
