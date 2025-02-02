package com.greenvenom.networking.api.utils

import com.greenvenom.networking.api.APINetworkError
import com.greenvenom.networking.domain.NetworkError
import com.greenvenom.networking.domain.ErrorType
import com.greenvenom.networking.data.Result
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException
import kotlin.coroutines.coroutineContext

suspend inline fun <reified T> safeCall (
    execute: () -> HttpResponse
): Result<T, NetworkError> {
    val response = try {
        execute()
    } catch (e: UnresolvedAddressException) {
        return Result.Error(APINetworkError(ErrorType.NO_INTERNET))
    } catch (e: SerializationException) {
        return Result.Error(APINetworkError(ErrorType.SERIALIZATION_ERROR))
    } catch (e: Exception) {
        coroutineContext.ensureActive()
        return Result.Error(APINetworkError(ErrorType.UNKNOWN_ERROR))
    }
    return responseToResult(response)
}