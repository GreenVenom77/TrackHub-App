package com.greevebite.auth.presentation.register.viewmodel

import androidx.lifecycle.viewModelScope
import com.greevebite.auth.domain.repository.RegisterRepository
import com.greevebite.auth.presentation.register.RegisterAction
import com.greevebite.auth.presentation.register.RegisterInputState
import com.greevebite.auth.util.ValidateInput
import com.greevebite.data.util.SupabaseError
import com.greevebite.domain.util.Result
import com.greevebite.domain.util.onError
import com.greevebite.ui.presentation.BaseViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val registerRepository: RegisterRepository
): BaseViewModel() {
    var registerState = MutableStateFlow(RegisterInputState())
        private set

    private val _registerExceptionHandler = CoroutineExceptionHandler { _, exception ->
        registerState.value = registerState.value.copy(
            registrationResult = Result.Error(SupabaseError(exception.message.toString())),
        )
        hideLoading()
        registerState.value.registrationResult?.onError { showErrorMessage(it.message) }
    }

    fun registerAction(action: RegisterAction) {
        when(action) {
            is RegisterAction.ValidateUsername -> {
                registerState.value = registerState.value.copy(
                    usernameValidity = ValidateInput.validateUsername(action.username)
                )
            }
            is RegisterAction.ValidateEmail -> {
                registerState.value = registerState.value.copy(
                    emailValidity = ValidateInput.validateEmail(action.email)
                )
            }
            is RegisterAction.ValidatePassword -> {
                registerState.value = registerState.value.copy(
                    passwordValidity = ValidateInput.validatePassword(action.password)
                )
            }
            is RegisterAction.ValidatePasswordConfirmation -> {
                registerState.value = registerState.value.copy(
                    confirmPasswordValidity = ValidateInput.validatePasswordConfirmation(
                        password = action.password,
                        confirmPassword = action.confirmPassword
                    )
                )
            }
            is RegisterAction.Register -> registerUser(
                username = action.username,
                email = action.email,
                password = action.password,
            )
            is RegisterAction.ResetRegisterState -> resetState()
            else -> Unit
        }
    }

    private fun registerUser(
        username: String,
        email: String,
        password: String,
    ) {
        hideErrorMessage()
        showLoading()
        viewModelScope.launch(_registerExceptionHandler) {
            registerRepository.registerUser(email, password, username)
        }.invokeOnCompletion {
            registerState.value = registerState.value.copy(
                registrationResult = Result.Success(Unit),
                accountEmail = email
            )
            hideLoading()
        }
    }

    private fun resetState() {
        registerState.value = RegisterInputState()
    }
}