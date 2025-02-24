package com.trackhub.hub.domain.repository

import com.greenvenom.networking.data.NetworkError
import com.greenvenom.networking.data.NetworkResult
import com.trackhub.domain.cache.hub.models.Hub
import com.trackhub.domain.cache.hub.models.HubItem
import kotlinx.coroutines.flow.Flow

interface HubRepository {
    suspend fun addNewHub(hub: Hub): NetworkResult<Unit, NetworkError>
    fun getOwnHubs(): Flow<NetworkResult<List<Hub>, NetworkError>>
    fun getSharedHubs(): Flow<NetworkResult<List<Hub>, NetworkError>>
    suspend fun addItemToHub(hubItem: HubItem): NetworkResult<Unit, NetworkError>
    fun getItemsFromHub(hubId: Int): Flow<NetworkResult<List<HubItem>, NetworkError>>
}