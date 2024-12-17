package com.greevebite.auth.register

import androidx.compose.runtime.Immutable
import com.greevebite.auth.util.ValidationError
import com.greevebite.data.util.SupabaseError
import com.greevebite.domain.util.Result

@Immutable
data class RegisterInputState(
    val emailValidity: Result<Unit, ValidationError>? = null,
    val usernameValidity: Result<Unit, ValidationError>? = null,
    val passwordValidity: Result<Unit, ValidationError>? = null,
    val confirmPasswordValidity: Result<Unit, ValidationError>? = null,
    val registrationResult: Result<Unit, SupabaseError>? = null,
    val accountEmail: String? = null,
)