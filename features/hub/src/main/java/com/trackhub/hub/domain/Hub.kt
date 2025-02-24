package com.trackhub.hub.domain

data class Hub(
    val id: Int? = null,
    val userId: String,
    val name: String,
    val createdAt: String?,
    val updatedAt: String?
)
