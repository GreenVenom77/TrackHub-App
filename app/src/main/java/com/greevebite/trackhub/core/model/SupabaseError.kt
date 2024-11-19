package com.greevebite.trackhub.core.model

data class SupabaseError(
    val message: String,
    val statusCode: Int? = null,
    val details: String? = null
)