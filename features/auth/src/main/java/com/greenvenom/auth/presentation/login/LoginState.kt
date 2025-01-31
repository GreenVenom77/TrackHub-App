package com.greenvenom.auth.presentation.login

import androidx.compose.runtime.Immutable
import com.greenvenom.validation.domain.ValidationError
import com.greenvenom.validation.domain.ValidationResult
import com.greenvenom.networking.data.Result
import com.greenvenom.networking.supabase.util.SupabaseError

@Immutable
data class LoginState(
    val emailValidity: ValidationResult<Unit, ValidationError>? = null,
    val passwordValidity: ValidationResult<Unit, ValidationError>? = null,
    val loginResult: Result<Unit, SupabaseError>? = null,
)