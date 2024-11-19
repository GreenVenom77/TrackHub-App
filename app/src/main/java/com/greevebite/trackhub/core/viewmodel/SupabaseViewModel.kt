package com.greevebite.trackhub.core.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greevebite.trackhub.core.data.remote.SupabaseClient
import com.greevebite.trackhub.core.model.OperationType
import com.greevebite.trackhub.core.model.SupabaseError
import com.greevebite.trackhub.core.model.SupabaseResponse
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SupabaseViewModel(private val supabaseClient: SupabaseClient): ViewModel() {
    private val _registerOperationState = MutableStateFlow<SupabaseResponse<String>>(SupabaseResponse.Idle)
    val registerOperationsState: StateFlow<SupabaseResponse<String>> get() = _registerOperationState

    private val _verifyOperationState = MutableStateFlow<SupabaseResponse<String>>(SupabaseResponse.Idle)
    val verifyOperationsState: StateFlow<SupabaseResponse<String>> get() = _verifyOperationState

    private val _loginOperationState = MutableStateFlow<SupabaseResponse<String>>(SupabaseResponse.Idle)
    val loginOperationsState: StateFlow<SupabaseResponse<String>> get() = _loginOperationState
    private var currentOperation: OperationType? = null

    private val _authHandler = CoroutineExceptionHandler { _, exception ->
        when (currentOperation) {
            OperationType.Register -> {
                _registerOperationState.value = SupabaseResponse.Failure(
                    SupabaseError(exception.localizedMessage ?: "Registration failed"),
                    OperationType.Register
                )
            }
            OperationType.Verify -> {
                _verifyOperationState.value = SupabaseResponse.Failure(
                    SupabaseError(exception.localizedMessage ?: "Verification failed"),
                    OperationType.Verify
                )
            }
            OperationType.Login -> {
                _loginOperationState.value = SupabaseResponse.Failure(
                    SupabaseError(exception.localizedMessage ?: "Login failed"),
                    OperationType.Login
                )
            }
            else -> {}
        }
    }

    fun registerUser(email: String, password: String, displayName: String) {
        viewModelScope.launch(_authHandler) {
            _registerOperationState.value = SupabaseResponse.Loading
            supabaseClient.registerUser(email, password, displayName)

            _registerOperationState.value = SupabaseResponse.Success(
                data = "Registration successful",
                operationType = OperationType.Register
            )
        }
    }

    fun verifyUserRegistration(email: String, otp: String) {
        viewModelScope.launch(_authHandler) {
            _verifyOperationState.value = SupabaseResponse.Loading
            supabaseClient.verifyUserRegistration(email, otp)

            _verifyOperationState.value = SupabaseResponse.Success(
                data = "Verification successful",
                operationType = OperationType.Verify
            )
        }
    }

    fun loginUser(email: String, password: String) {
        viewModelScope.launch(_authHandler) {
            _loginOperationState.value = SupabaseResponse.Loading
            currentOperation = OperationType.Login
            supabaseClient.loginUser(email, password)

            _loginOperationState.value = SupabaseResponse.Success(
                data = "Login successful",
                operationType = OperationType.Login
            )
        }
    }

    fun resetAuthOperationsState(operationType: OperationType) {
        when (operationType) {
            OperationType.Register -> _registerOperationState.value = SupabaseResponse.Idle
            OperationType.Verify -> _verifyOperationState.value = SupabaseResponse.Idle
            OperationType.Login -> _loginOperationState.value = SupabaseResponse.Idle
        }
        currentOperation = null
    }
}