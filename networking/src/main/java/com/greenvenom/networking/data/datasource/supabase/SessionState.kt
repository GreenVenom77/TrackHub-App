package com.greenvenom.networking.data.datasource.supabase

import io.github.jan.supabase.auth.status.SessionStatus

data class SessionState(
    val currentStatus: SessionStatus? = null,
)
