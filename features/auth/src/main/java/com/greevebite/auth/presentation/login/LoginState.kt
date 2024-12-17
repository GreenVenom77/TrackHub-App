package com.greevebite.auth.presentation.login

import androidx.compose.runtime.Immutable
import com.greevebite.auth.util.ValidationError
import com.greevebite.data.util.SupabaseError
import com.greevebite.domain.util.Result

@Immutable
data class LoginState(
    val emailValidity: Result<Unit, ValidationError>? = null,
    val passwordValidity: Result<Unit, ValidationError>? = null,
    val loginResult: Result<Unit, SupabaseError>? = null,
)
