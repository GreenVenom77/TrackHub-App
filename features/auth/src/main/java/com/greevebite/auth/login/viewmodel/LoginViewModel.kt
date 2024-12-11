package com.greevebite.auth.login.viewmodel

import androidx.lifecycle.viewModelScope
import com.greevebite.auth.domain.repository.AuthRepository
import com.greevebite.auth.login.LoginAction
import com.greevebite.auth.login.LoginState
import com.greevebite.auth.util.ValidateInput
import com.greevebite.data.util.SupabaseError
import com.greevebite.domain.util.OperationType
import com.greevebite.domain.util.Result
import com.greevebite.ui.presentation.BaseViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository
): BaseViewModel() {
    var loginState = MutableStateFlow(LoginState())
        private set

    private val _authHandler = CoroutineExceptionHandler { _, exception ->
        when (loginState.value.currentOperation) {
            OperationType.LOGIN -> {
                loginState.value = loginState.value.copy(
                    loginResult = Result.Error(SupabaseError(exception.localizedMessage ?: "Login failed")),
                    currentOperation = null
                )
            }
            else -> {}
        }
    }

    fun loginAction(action: LoginAction) {
        when(action) {
            is LoginAction.ValidateEmail -> {
                loginState.value = loginState.value.copy(
                    emailValidity = ValidateInput.validateEmail(action.email)
                )
            }
            is LoginAction.ValidatePassword -> {
                loginState.value = loginState.value.copy(
                    passwordValidity = ValidateInput.validateLoginPassword(action.password)
                )
            }
            is LoginAction.Login -> {
                loginState.value = loginState.value.copy(
                    currentOperation = OperationType.LOGIN
                )
                login(
                    email = action.email,
                    password = action.password
                )
            }

            else -> Unit
        }
    }

    private fun login(email: String, password: String) {
        hideErrorMessage()
        showLoading()
        viewModelScope.launch(_authHandler) {
            authRepository.loginUser(email, password)
        }.invokeOnCompletion {
            loginState.value = loginState.value.copy(
                loginResult = Result.Success(Unit),
                currentOperation = null
            )
            hideLoading()
        }
    }
}