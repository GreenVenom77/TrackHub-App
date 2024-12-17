package com.greevebite.auth.presentation

import androidx.lifecycle.viewModelScope
import com.greevebite.auth.domain.repository.AccountRepository
import com.greevebite.ui.presentation.BaseViewModel
import kotlinx.coroutines.launch

class AccountViewModel(
    private val accountRepository: AccountRepository
): BaseViewModel() {
    fun accountAction(action: AccountAction) {
        when(action) {
            is AccountAction.Logout -> logoutUser()
        }
    }

    private fun logoutUser() {
        viewModelScope.launch {
            accountRepository.logoutUser()
        }
    }
}