package com.trackhub.core_hub.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Hub(
    val id: String = "",
    val userId: String = "",
    val name: String,
    val description: String? = null,
    val createdAt: String = "",
)
