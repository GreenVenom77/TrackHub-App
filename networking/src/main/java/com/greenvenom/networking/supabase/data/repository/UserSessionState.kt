package com.greenvenom.networking.supabase.data.repository

import com.greenvenom.networking.supabase.domain.SessionDestinations

data class UserSessionState(
    val wantedDestination: SessionDestinations? = null,
)
