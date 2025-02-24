package com.trackhub.data.datasource.remote.hub.dto

import com.trackhub.domain.cache.hub.models.Hub
import kotlinx.serialization.Serializable

@Serializable
data class HubDto(
    val id: Int? = null,
    val userId: String,
    val name: String,
    val createdAt: String? = null,
    val updatedAt: String? = null
)

fun Hub.toHubDto(): HubDto {
    return HubDto(
        id = this.id,
        name = this.name,
        userId = this.userId,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}

fun HubDto.toHub(): Hub {
    return Hub(
        id = this.id,
        userId = this.userId,
        name = this.name,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}