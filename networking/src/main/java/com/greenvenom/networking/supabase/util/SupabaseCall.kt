package com.greenvenom.networking.supabase.util

import com.greenvenom.networking.data.ErrorType
import com.greenvenom.networking.data.NetworkError
import com.greenvenom.networking.data.NetworkResult
import com.greenvenom.networking.utils.toNetworkError
import io.github.jan.supabase.exceptions.HttpRequestException
import io.github.jan.supabase.exceptions.RestException
import io.ktor.client.plugins.HttpRequestTimeoutException

suspend inline fun <reified T> supabaseCall (
    execute: () -> T
): NetworkResult<T, NetworkError> {
    val result = try {
        val executionResult = execute()
        NetworkResult.Success(executionResult)
    } catch (exception: RestException) {
        NetworkResult.Error(toNetworkError(exception.statusCode))
    } catch (exception: HttpRequestException) {
        NetworkResult.Error(NetworkError(ErrorType.NO_INTERNET))
    } catch (exception: HttpRequestTimeoutException) {
        NetworkResult.Error(NetworkError(ErrorType.REQUEST_TIMEOUT))
    }
    return result
}