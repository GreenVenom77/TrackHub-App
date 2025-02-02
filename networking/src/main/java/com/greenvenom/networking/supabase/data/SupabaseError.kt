package com.greenvenom.networking.supabase.util

import com.greenvenom.networking.data.Error

data class SupabaseError(
    override val message: String = "",
    val description: String? = null,
): Error()