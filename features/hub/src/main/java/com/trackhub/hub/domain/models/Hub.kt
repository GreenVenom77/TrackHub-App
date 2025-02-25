package com.trackhub.hub.domain.models

data class Hub(
    val id: Int? = null,
    val userId: String,
    val name: String,
    val createdAt: String? = null,
    val updatedAt: String? = null
)
