package com.trackhub.hub.domain.repository

import com.greenvenom.networking.data.NetworkError
import com.greenvenom.networking.data.NetworkResult
import com.trackhub.hub.domain.models.Hub
import com.trackhub.hub.domain.models.HubItem
import kotlinx.coroutines.flow.Flow

interface HubRepository {
    suspend fun addHub(hub: Hub): NetworkResult<Unit, NetworkError>
    fun getHubs(isOwned: Boolean = true): Flow<NetworkResult<List<Hub>, NetworkError>>
    suspend fun addItemToHub(hubItem: HubItem): NetworkResult<Unit, NetworkError>
    fun getItemsFromHub(hubId: Int): Flow<NetworkResult<List<HubItem>, NetworkError>>
}