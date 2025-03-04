package com.greenvenom.core_hub.domain.models

data class Hub(
    val id: String = "",
    val userId: String = "",
    val name: String,
    val createdAt: String? = null,
    val updatedAt: String? = null
)
