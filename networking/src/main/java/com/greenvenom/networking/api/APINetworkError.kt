package com.greenvenom.networking.api

import com.greenvenom.networking.domain.ErrorType
import com.greenvenom.networking.domain.NetworkError

data class APINetworkError(
    override val errorType: ErrorType = ErrorType.UNKNOWN_ERROR,
): NetworkError()
