package com.greevebite.trackhub.core.model

sealed class SupabaseResponse<out T> {
    data object Idle : SupabaseResponse<Nothing>()
    data object Loading : SupabaseResponse<Nothing>()
    data class Success<out T>(val data: T, val operationType: OperationType) : SupabaseResponse<T>()
    data class Failure(val error: SupabaseError, val operationType: OperationType) : SupabaseResponse<Nothing>()
}

enum class OperationType {
    Register, Verify, Login
}