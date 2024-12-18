package com.greevebite.auth.presentation.otp

import com.greevebite.data.util.SupabaseError
import com.greevebite.domain.util.Result

data class OtpState(
    val code: List<Int?> = (1..4).map { null },
    val email: String? = null,
    val focusedIndex: Int? = null,
    val otpResult: Result<Unit, SupabaseError>? = null
)