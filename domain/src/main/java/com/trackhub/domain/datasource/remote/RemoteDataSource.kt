package com.trackhub.domain.datasource.remote

import com.greenvenom.networking.data.NetworkError
import com.greenvenom.networking.data.NetworkResult
import com.trackhub.hub.domain.Hub
import com.trackhub.hub.domain.HubItem

interface RemoteDataSource {
    // Authentication
    suspend fun registerUser(displayName: String, email: String, password: String): NetworkResult<Any?, NetworkError>
    suspend fun loginUser(email: String, password: String): NetworkResult<Any, NetworkError>
    suspend fun verifyOtp(email: String, otp: String): NetworkResult<Any, NetworkError>
    suspend fun sendResetPasswordEmail(email: String): NetworkResult<Any, NetworkError>
    suspend fun updatePassword(password: String): NetworkResult<Any, NetworkError>
    suspend fun logoutUser(): NetworkResult<Any, NetworkError>

    // Hubs
    suspend fun addNewHub(hub: Hub): NetworkResult<Unit, NetworkError>
    suspend fun getOwnHubs(): NetworkResult<List<Hub>, NetworkError>
    suspend fun getSharedHubs(): NetworkResult<List<Hub>, NetworkError>
    suspend fun addItemToHub(hubItem: HubItem): NetworkResult<Unit, NetworkError>
    suspend fun getItemsFromHub(hubId: Int): NetworkResult<List<HubItem>, NetworkError>
}