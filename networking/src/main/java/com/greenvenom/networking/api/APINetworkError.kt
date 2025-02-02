package com.greenvenom.networking.api

import com.greenvenom.networking.domain.NetworkError
import com.greenvenom.networking.domain.ErrorType

data class APINetworkError(
    override val errorType: ErrorType = ErrorType.UNKNOWN_ERROR,
): NetworkError()
