package com.greenvenom.networking.api.utils

import com.greenvenom.networking.domain.Error
import com.greenvenom.networking.domain.ErrorType
import com.greenvenom.networking.data.Result
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException
import kotlin.coroutines.coroutineContext

suspend inline fun <reified T> safeCall (
    execute: () -> HttpResponse
): Result<T, Error> {
    val response = try {
        execute()
    } catch (e: UnresolvedAddressException) {
        return Result.Error(ErrorType.NO_INTERNET)
    } catch (e: SerializationException) {
        return Result.Error(ErrorType.SERIALIZATION_ERROR)
    } catch (e: Exception) {
        coroutineContext.ensureActive()
        return Result.Error(ErrorType.UNKNOWN_ERROR)
    }
    return responseToResult(response)
}