package com.greenvenom.auth.presentation.otp

import androidx.compose.runtime.Immutable
import com.greenvenom.networking.data.Result
import com.greenvenom.networking.data.datasource.supabase.util.SupabaseError

@Immutable
data class OtpState(
    val code: List<Int?> = (1..6).map { null },
    val focusedIndex: Int? = null,
    val otpResult: Result<Unit, SupabaseError>? = null
)