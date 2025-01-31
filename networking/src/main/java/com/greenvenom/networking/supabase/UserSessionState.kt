package com.greenvenom.networking.supabase

import com.greenvenom.networking.supabase.util.SessionDestinations

data class UserSessionState(
    val wantedDestination: SessionDestinations? = null,
)
