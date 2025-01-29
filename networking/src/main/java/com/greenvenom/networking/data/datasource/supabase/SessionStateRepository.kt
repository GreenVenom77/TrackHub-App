package com.greenvenom.networking.data.datasource.supabase

import com.greenvenom.networking.data.datasource.supabase.util.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SessionStateRepository(supabaseClient: SupabaseClient) {
    private val client = supabaseClient.getClient()
    private val _sessionStatus = MutableStateFlow<SessionStatus>(SessionStatus.Initializing)
    val sessionStatus = _sessionStatus.asStateFlow()

    fun collectSessionStatus() {
        CoroutineScope(Dispatchers.IO).launch {
            client.auth.sessionStatus.collect {
                _sessionStatus.value = it
            }
        }
    }
}