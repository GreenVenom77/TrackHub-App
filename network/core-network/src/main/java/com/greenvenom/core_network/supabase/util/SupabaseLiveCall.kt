package com.greenvenom.core_network.supabase.util

import com.greenvenom.core_network.data.ErrorType
import com.greenvenom.core_network.data.NetworkError
import com.greenvenom.core_network.data.NetworkResult
import com.greenvenom.core_network.utils.toNetworkError
import io.github.jan.supabase.exceptions.HttpRequestException
import io.github.jan.supabase.exceptions.RestException
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.plugins.HttpRequestTimeoutException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow

inline fun <reified T> supabaseLiveCall (
    crossinline execute: () -> Flow<T>
): Flow<NetworkResult<T, NetworkError>> {
    return channelFlow {
        try {
            execute().collect { data ->
                try {
                    send(NetworkResult.Success(data))
                } catch (e: NoTransformationFoundException) {
                    send(NetworkResult.Error(NetworkError(ErrorType.SERIALIZATION_ERROR)))
                }
            }
        } catch (exception: RestException) {
            send(NetworkResult.Error(toNetworkError(exception.statusCode)))
        } catch (exception: HttpRequestException) {
            send(NetworkResult.Error(NetworkError(ErrorType.NO_INTERNET)))
        } catch (exception: HttpRequestTimeoutException) {
            send(NetworkResult.Error(NetworkError(ErrorType.REQUEST_TIMEOUT)))
        }
    }
}