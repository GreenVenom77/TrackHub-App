package com.greenvenom.networking.supabase.util

import com.greenvenom.networking.data.ErrorType
import com.greenvenom.networking.data.NetworkError
import com.greenvenom.networking.data.NetworkResult
import com.greenvenom.networking.utils.toNetworkError
import io.github.jan.supabase.exceptions.HttpRequestException
import io.github.jan.supabase.exceptions.RestException
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.plugins.HttpRequestTimeoutException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

inline fun <reified T> supabaseLiveCall (
    crossinline execute: () -> Flow<T>
): Flow<NetworkResult<T, NetworkError>> = flow {
    try {
        execute().collect { data ->
            try {
                emit(NetworkResult.Success(data))
            } catch (e: NoTransformationFoundException) {
                emit(NetworkResult.Error(NetworkError(ErrorType.SERIALIZATION_ERROR)))
            }
        }
    } catch (exception: RestException) {
        emit(NetworkResult.Error(toNetworkError(exception.statusCode)))
    } catch (exception: HttpRequestException) {
        emit(NetworkResult.Error(NetworkError(ErrorType.NO_INTERNET)))
    } catch (exception: HttpRequestTimeoutException) {
        emit(NetworkResult.Error(NetworkError(ErrorType.REQUEST_TIMEOUT)))
    }
}