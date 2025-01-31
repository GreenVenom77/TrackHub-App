package com.greenvenom.auth.presentation.reset_password

import androidx.compose.runtime.Immutable
import com.greenvenom.validation.domain.ValidationError
import com.greenvenom.validation.domain.ValidationResult
import com.greenvenom.networking.data.Result
import com.greenvenom.networking.supabase.util.SupabaseError

@Immutable
data class ResetPasswordState(
    val passwordValidity: ValidationResult<Unit, ValidationError>? = null,
    val confirmPasswordValidity: ValidationResult<Unit, ValidationError>? = null,
    val emailSentResult: Result<Unit, SupabaseError>? = null,
    val passwordUpdatedResult: Result<Unit, SupabaseError>? = null
)