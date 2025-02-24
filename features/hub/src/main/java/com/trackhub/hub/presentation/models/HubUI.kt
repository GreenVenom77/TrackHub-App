package com.trackhub.hub.presentation.models

data class HubUI(
    val id: Int? = null,
    val userId: String,
    val name: String,
    val createdAt: String?,
    val updatedAt: String?
)