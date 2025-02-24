package com.trackhub.hub.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class HubDto(
    val id: Int? = null,
    val userId: String,
    val name: String,
    val createdAt: String?,
    val updatedAt: String?
)