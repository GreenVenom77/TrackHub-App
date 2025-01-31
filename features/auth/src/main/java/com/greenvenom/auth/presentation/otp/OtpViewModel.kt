package com.greenvenom.auth.presentation.otp

import androidx.lifecycle.viewModelScope
import com.greenvenom.auth.data.repository.EmailStateRepository
import com.greenvenom.auth.domain.repository.OtpRepository
import com.greenvenom.networking.data.Result
import com.greenvenom.networking.supabase.util.SupabaseError
import com.greenvenom.networking.data.onError
import com.greenvenom.ui.presentation.BaseViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OtpViewModel(
    private val emailStateRepository: EmailStateRepository,
    private val otpRepository: OtpRepository
): BaseViewModel() {
    private val _otpState = MutableStateFlow(OtpState())
    val otpState = _otpState.asStateFlow()

    private val _otpExceptionHandler = CoroutineExceptionHandler { _, exception ->
        _otpState.update { _otpState.value.copy(
            otpResult = Result.Error(SupabaseError(exception.message.toString())),
        )}
        hideLoading()
        _otpState.value.otpResult?.onError { showErrorMessage(it.message) }
    }

    fun otpAction(action: OtpAction) {
        when(action) {
            is OtpAction.OnChangeFieldFocused -> {
                _otpState.update { it.copy(
                    focusedIndex = action.index
                ) }
            }
            is OtpAction.OnEnterNumber -> {
                enterNumber(action.number, action.index)
            }

            is OtpAction.OnKeyboardBack -> {
                val previousIndex = getPreviousFocusedIndex(_otpState.value.focusedIndex)
                _otpState.update { it.copy(
                    code = it.code.mapIndexed { index, number ->
                        if(index == previousIndex) {
                            null
                        } else {
                            number
                        }
                    },
                    focusedIndex = previousIndex
                ) }
            }
            is OtpAction.ResetState -> {
                resetOtpState()
            }
        }
    }

    private fun verifyOtp(email: String, otp: String) {
        showLoading()
        viewModelScope.launch(_otpExceptionHandler) {
            otpRepository.verifyOtp(email, otp)
        }.invokeOnCompletion {
            _otpState.update { it.copy(otpResult = Result.Success(Unit)) }
            hideLoading()
        }
    }

    private fun enterNumber(number: Int?, index: Int) {
        val newCode = _otpState.value.code.mapIndexed { currentIndex, currentNumber ->
            if(currentIndex == index) {
                number
            } else {
                currentNumber
            }
        }
        val wasNumberRemoved = number == null
        _otpState.update { it.copy(
            code = newCode,
            focusedIndex = if(wasNumberRemoved || it.code.getOrNull(index) != null) {
                it.focusedIndex
            } else {
                getNextFocusedTextFieldIndex(
                    currentCode = it.code,
                    currentFocusedIndex = it.focusedIndex
                )
            },
        ) }
        if (newCode.all { it != null }) {
            val otp = newCode.joinToString("") { it.toString() }
            emailStateRepository.emailState.value.email?.let { verifyOtp(it, otp) }
        }
    }

    private fun getPreviousFocusedIndex(currentIndex: Int?): Int? {
        return currentIndex?.minus(1)?.coerceAtLeast(0)
    }

    private fun getNextFocusedTextFieldIndex(
        currentCode: List<Int?>,
        currentFocusedIndex: Int?
    ): Int? {
        if(currentFocusedIndex == null) {
            return null
        }

        if(currentFocusedIndex == 5) {
            return currentFocusedIndex
        }

        return getFirstEmptyFieldIndexAfterFocusedIndex(
            code = currentCode,
            currentFocusedIndex = currentFocusedIndex
        )
    }

    private fun getFirstEmptyFieldIndexAfterFocusedIndex(
        code: List<Int?>,
        currentFocusedIndex: Int
    ): Int {
        code.forEachIndexed { index, number ->
            if(index <= currentFocusedIndex) {
                return@forEachIndexed
            }
            if(number == null) {
                return index
            }
        }
        return currentFocusedIndex
    }

    private fun resetOtpState() {
        _otpState.value = OtpState()
    }
}