package com.greevebite.auth.login.viewmodel

import androidx.lifecycle.viewModelScope
import com.greevebite.auth.domain.repository.LoginRepository
import com.greevebite.auth.login.LoginAction
import com.greevebite.auth.login.LoginState
import com.greevebite.auth.util.ValidateInput
import com.greevebite.data.util.SupabaseError
import com.greevebite.domain.util.Result
import com.greevebite.domain.util.onError
import com.greevebite.ui.presentation.BaseViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginRepository: LoginRepository
): BaseViewModel() {
    var loginState = MutableStateFlow(LoginState())
        private set

    private val _loginExceptionHandler = CoroutineExceptionHandler { _, exception ->
        loginState.value = loginState.value.copy(
            loginResult = Result.Error(SupabaseError(exception.message.toString())),
        )
        hideLoading()
        loginState.value.loginResult?.onError { showErrorMessage(it.message) }
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
                login(
                    email = action.email,
                    password = action.password
                )
            }
            is LoginAction.ResetLoginState -> resetLoginState()
            else -> Unit
        }
    }

    private fun login(email: String, password: String) {
        hideErrorMessage()
        showLoading()
        viewModelScope.launch(_loginExceptionHandler) {
            loginRepository.loginUser(email, password)
        }.invokeOnCompletion {
            loginState.value = loginState.value.copy(
                loginResult = Result.Success(Unit),
            )
            hideLoading()
        }
    }

    private fun resetLoginState() {
        loginState.value = LoginState()
    }
}