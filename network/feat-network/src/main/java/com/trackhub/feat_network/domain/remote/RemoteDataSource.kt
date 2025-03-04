package com.trackhub.feat_network.domain.remote

import com.greenvenom.core_network.data.NetworkError
import com.greenvenom.core_network.data.NetworkResult
import com.trackhub.core_hub.domain.models.Hub
import com.trackhub.core_hub.domain.models.HubItem
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    // Authentication
    suspend fun registerUser(displayName: String, email: String, password: String): NetworkResult<Any?, NetworkError>
    suspend fun loginUser(email: String, password: String): NetworkResult<Any, NetworkError>
    suspend fun verifyOtp(email: String, otp: String): NetworkResult<Any, NetworkError>
    suspend fun sendResetPasswordEmail(email: String): NetworkResult<Any, NetworkError>
    suspend fun updatePassword(password: String): NetworkResult<Any, NetworkError>
    suspend fun logoutUser(): NetworkResult<Any, NetworkError>

    // Hubs
    suspend fun addHub(hub: Hub): NetworkResult<Hub, NetworkError>
    suspend fun getOwnHubs(): NetworkResult<List<Hub>, NetworkError>
    suspend fun getSharedHubs(): NetworkResult<List<Hub>, NetworkError>
    suspend fun addItemToHub(hubItem: HubItem): NetworkResult<Unit, NetworkError>
    fun getItemsFromHub(hubId: String): Flow<NetworkResult<List<HubItem>, NetworkError>>
}