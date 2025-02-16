package com.greenvenom.auth.presentation.reset_password

import androidx.compose.runtime.Immutable
import com.greenvenom.networking.data.NetworkError
import com.greenvenom.networking.data.NetworkResult
import com.greenvenom.validation.domain.ValidationError
import com.greenvenom.validation.domain.ValidationResult

@Immutable
data class ResetPasswordState(
    val passwordValidity: ValidationResult<Unit, ValidationError>? = null,
    val confirmPasswordValidity: ValidationResult<Unit, ValidationError>? = null,
    val passwordUpdatedNetworkResult: NetworkResult<Any, NetworkError>? = null,
    val emailSentNetworkResult: NetworkResult<Any, NetworkError>? = null
)