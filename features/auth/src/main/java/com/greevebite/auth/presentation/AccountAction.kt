package com.greevebite.auth.presentation

sealed interface AccountAction {
    data object Logout: AccountAction
}