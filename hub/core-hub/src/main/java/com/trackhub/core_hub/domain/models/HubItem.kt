package com.trackhub.core_hub.domain.models

import java.math.BigDecimal

data class HubItem(
    val id: Int = 0,
    val hubId: String,
    val name: String,
    val stockCount: BigDecimal,
    val unit: String,
    val imageUrl: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
)
