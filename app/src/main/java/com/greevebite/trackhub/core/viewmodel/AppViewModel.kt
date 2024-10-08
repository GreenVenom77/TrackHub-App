package com.greevebite.trackhub.core.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greevebite.trackhub.core.data.remote.SupabaseClient
import kotlinx.coroutines.launch

class AppViewModel(private val supabaseClient: SupabaseClient): ViewModel() {
    fun registerUser(email: String, password: String, displayName: String) {
        viewModelScope.launch {
            supabaseClient.registerUser(email, password, displayName)
        }
    }

    fun verifyUserRegistration(email: String, otp: String) {
        viewModelScope.launch {
            supabaseClient.verifyUserRegistration(email, otp)
        }
    }

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            supabaseClient.loginUser(email, password)
        }
    }
}