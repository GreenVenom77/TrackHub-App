package com.trackhub.hub.data.repository

import com.greenvenom.networking.data.NetworkError
import com.greenvenom.networking.data.NetworkResult
import com.greenvenom.networking.data.onError
import com.greenvenom.networking.data.onSuccess
import com.trackhub.domain.cache.CacheDataSource
import com.trackhub.domain.remote.RemoteDataSource
import com.trackhub.domain.cache.hub.models.Hub
import com.trackhub.domain.cache.hub.models.HubItem
import com.trackhub.hub.domain.repository.HubRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HubRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val cacheDataSource: CacheDataSource
): HubRepository {
    override suspend fun addNewHub(hub: Hub): NetworkResult<Unit, NetworkError> {
        val remoteResult = remoteDataSource.addNewHub(hub)
        remoteResult.onSuccess { cacheDataSource.addNewOwnHub(hub) }
        return remoteResult
    }

    override fun getOwnHubs(): Flow<NetworkResult<List<Hub>, NetworkError>> = flow {
        val cachedOwnHubs = cacheDataSource.getOwnHubs()
        if (cachedOwnHubs.isNotEmpty()) emit(NetworkResult.Success(cachedOwnHubs))

        val remoteOwnHubs = remoteDataSource.getOwnHubs()
        remoteOwnHubs.onSuccess {
            val newOwnHubs = it.toSet().filter { hub -> !cachedOwnHubs.toSet().contains(hub) }
            if (newOwnHubs.isNotEmpty()) cacheDataSource.updateOwnHubs(newOwnHubs)
            emit(NetworkResult.Success(it))
        }
        remoteOwnHubs.onError { emit(NetworkResult.Error(it)) }
    }

    override fun getSharedHubs(): Flow<NetworkResult<List<Hub>, NetworkError>> = flow {
        val cachedSharedHubs = cacheDataSource.getSharedHubs()
        if (cachedSharedHubs.isNotEmpty()) emit(NetworkResult.Success(cachedSharedHubs))

        val remoteSharedHubs = remoteDataSource.getSharedHubs()
        remoteSharedHubs.onSuccess {
            val newSharedHubs = it.toSet().filter { hub -> !cachedSharedHubs.toSet().contains(hub) }
            if (newSharedHubs.isNotEmpty()) cacheDataSource.updateSharedHubs(newSharedHubs)
            emit(NetworkResult.Success(it))
        }
        remoteSharedHubs.onError { emit(NetworkResult.Error(it)) }
    }

    override suspend fun addItemToHub(hubItem: HubItem): NetworkResult<Unit, NetworkError> {
        val remoteResult = remoteDataSource.addItemToHub(hubItem)
        remoteResult.onSuccess { cacheDataSource.addNewItem(hubItem) }
        return remoteResult
    }

    override fun getItemsFromHub(hubId: Int): Flow<NetworkResult<List<HubItem>, NetworkError>> = flow {
        val cachedItems = cacheDataSource.getItemsFromHub(hubId)
        if (cachedItems.isNotEmpty()) emit(NetworkResult.Success(cachedItems))

        val remoteItems = remoteDataSource.getItemsFromHub(hubId)
        remoteItems.onSuccess {
            val newItems = it.toSet().filter { item -> !cachedItems.toSet().contains(item) }
            if (newItems.isNotEmpty()) cacheDataSource.addNewItems(newItems)
            emit(NetworkResult.Success(it))
        }
        remoteItems.onError { emit(NetworkResult.Error(it)) }
    }
}