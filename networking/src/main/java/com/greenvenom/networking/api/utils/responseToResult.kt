package com.greenvenom.networking.api.utils

import com.greenvenom.networking.domain.Error
import com.greenvenom.networking.domain.ErrorType
import com.greenvenom.networking.data.Result
import io.ktor.client.statement.HttpResponse
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body

suspend inline fun <reified T> responseToResult(
    response: HttpResponse
): Result<T, Error> {
    return when (response.status.value) {
        in 200..299 -> {
            try {
                Result.Success(response.body<T>())
            } catch (e: NoTransformationFoundException) {
                Result.Error()
            }
        }
        408 -> Result.Error(ErrorType.REQUEST_TIMEOUT)
        429 -> Result.Error(ErrorType.TOO_MANY_REQUESTS)
        in 500..599 -> Result.Error(ErrorType.SERVER_ERROR)
        else -> Result.Error(ErrorType.UNKNOWN_ERROR)
    }
}