package com.greevebite.auth

sealed interface AccountAction {
    data object Logout: AccountAction
}