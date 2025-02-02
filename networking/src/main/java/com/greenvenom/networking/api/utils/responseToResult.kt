package com.greenvenom.networking.api.utils

import com.greenvenom.networking.api.APINetworkError
import com.greenvenom.networking.domain.NetworkError
import com.greenvenom.networking.domain.ErrorType
import com.greenvenom.networking.data.Result
import io.ktor.client.statement.HttpResponse
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body

suspend inline fun <reified T> responseToResult(
    response: HttpResponse
): Result<T, NetworkError> {
    return when (response.status.value) {
        in 200..299 -> {
            try {
                Result.Success(response.body<T>())
            } catch (e: NoTransformationFoundException) {
                Result.Error(APINetworkError(ErrorType.SERIALIZATION_ERROR))
            }
        }
        408 -> Result.Error(APINetworkError(ErrorType.REQUEST_TIMEOUT))
        429 -> Result.Error(APINetworkError(ErrorType.TOO_MANY_REQUESTS))
        in 500..599 -> Result.Error(APINetworkError(ErrorType.SERVER_ERROR))
        else -> Result.Error(APINetworkError(ErrorType.UNKNOWN_ERROR))
    }
}