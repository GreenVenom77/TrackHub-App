package com.greevebite.data.datasource.remote

import com.greevebite.domain.datasource.RemoteDataSource
import io.github.jan.supabase.auth.OtpType
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class SupabaseDataSource(private val supabaseClient: SupabaseClient): RemoteDataSource {
    val client = supabaseClient.getClient()

    override suspend fun registerUser(email: String, password: String, displayName: String) {
        client.auth.signUpWith(Email) {
            this.email = email
            this.password = password
            this.data = buildJsonObject { put("display_name", displayName) }
        }
    }

    override suspend fun verifyUserRegistration(email: String, otp: String) {
        client.auth.verifyEmailOtp(OtpType.Email.EMAIL, email, otp)
    }

    override suspend fun loginUser(email: String, password: String) {
        client.auth.signInWith(Email) {
            this.email = email
            this.password = password
        }
    }

    override suspend fun logoutUser() {
        client.auth.signOut()
    }
//    private val _registerOperationState = MutableStateFlow<SupabaseResponse<String>>(SupabaseResponse.Idle)
//    val registerOperationsState: StateFlow<SupabaseResponse<String>> get() = _registerOperationState
//
//    private val _verifyOperationState = MutableStateFlow<SupabaseResponse<String>>(SupabaseResponse.Idle)
//    val verifyOperationsState: StateFlow<SupabaseResponse<String>> get() = _verifyOperationState
//
//    private val _loginOperationState = MutableStateFlow<SupabaseResponse<String>>(SupabaseResponse.Idle)
//    val loginOperationsState: StateFlow<SupabaseResponse<String>> get() = _loginOperationState
//    private var currentOperation: OperationType? = null
//
//    private val _authHandler = CoroutineExceptionHandler { _, exception ->
//        when (currentOperation) {
//            OperationType.Register -> {
//                _registerOperationState.value = SupabaseResponse.Failure(
//                    SupabaseError(exception.localizedMessage ?: "Registration failed"),
//                    OperationType.Register
//                )
//            }
//            OperationType.Verify -> {
//                _verifyOperationState.value = SupabaseResponse.Failure(
//                    SupabaseError(exception.localizedMessage ?: "Verification failed"),
//                    OperationType.Verify
//                )
//            }
//            OperationType.Login -> {
//                _loginOperationState.value = SupabaseResponse.Failure(
//                    SupabaseError(exception.localizedMessage ?: "Login failed"),
//                    OperationType.Login
//                )
//            }
//            else -> {}
//        }
//    }
//
//    fun registerUser(email: String, password: String, displayName: String) {
//        viewModelScope.launch(_authHandler) {
//            _registerOperationState.value = SupabaseResponse.Loading
//            supabaseClient.registerUser(email, password, displayName)
//
//            _registerOperationState.value = SupabaseResponse.Success(
//                data = "Registration successful",
//                operationType = OperationType.Register
//            )
//        }
//    }
//
//    fun verifyUserRegistration(email: String, otp: String) {
//        viewModelScope.launch(_authHandler) {
//            _verifyOperationState.value = SupabaseResponse.Loading
//            supabaseClient.verifyUserRegistration(email, otp)
//
//            _verifyOperationState.value = SupabaseResponse.Success(
//                data = "Verification successful",
//                operationType = OperationType.Verify
//            )
//        }
//    }
//
//    fun loginUser(email: String, password: String) {
//        viewModelScope.launch(_authHandler) {
//            _loginOperationState.value = SupabaseResponse.Loading
//            currentOperation = OperationType.Login
//            supabaseClient.loginUser(email, password)
//
//            _loginOperationState.value = SupabaseResponse.Success(
//                data = "Login successful",
//                operationType = OperationType.Login
//            )
//        }
//    }
//
//    fun resetAuthOperationsState(operationType: OperationType) {
//        when (operationType) {
//            OperationType.Register -> _registerOperationState.value = SupabaseResponse.Idle
//            OperationType.Verify -> _verifyOperationState.value = SupabaseResponse.Idle
//            OperationType.Login -> _loginOperationState.value = SupabaseResponse.Idle
//        }
//        currentOperation = null
//    }
}