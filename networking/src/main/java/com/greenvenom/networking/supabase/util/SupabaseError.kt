package com.greenvenom.networking.supabase.util

import com.greenvenom.networking.data.Error

data class SupabaseError(
    val message: String,
): Error