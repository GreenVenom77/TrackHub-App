package com.greevebite.data.util

import com.greevebite.domain.util.Error

data class SupabaseError(
    val message: String,
): Error