package com.greenvenom.networking.data.datasource

import com.greenvenom.networking.data.Error

data class SupabaseError(
    val message: String,
): Error