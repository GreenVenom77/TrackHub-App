package com.skewnexus.trackhub.data.features.hub

import com.greenvenom.networking.data.NetworkError
import com.greenvenom.networking.data.NetworkResult
import com.greenvenom.networking.data.onError
import com.greenvenom.networking.data.onSuccess
import com.skewnexus.trackhub.domain.cache.CacheDataSource
import com.skewnexus.trackhub.domain.remote.RemoteDataSource
import com.trackhub.hub.domain.models.Hub
import com.trackhub.hub.domain.models.HubItem
import com.trackhub.hub.domain.repository.HubRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HubRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val cacheDataSource: CacheDataSource
): HubRepository {
    override suspend fun addHub(hub: Hub): NetworkResult<Unit, NetworkError> {
        val remoteResult = remoteDataSource.addHub(hub)
        remoteResult.onSuccess { cacheDataSource.addHub(hub) }
        return remoteResult
    }

    override fun getHubs(isOwned: Boolean): Flow<NetworkResult<List<Hub>, NetworkError>> = flow {
        val cachedHubs = if (isOwned) cacheDataSource.getOwnHubs() else cacheDataSource.getSharedHubs()
        if (cachedHubs.isNotEmpty()) emit(NetworkResult.Success(cachedHubs))

        val remoteHubs = if (isOwned) remoteDataSource.getOwnHubs() else remoteDataSource.getSharedHubs()
        remoteHubs.onSuccess {
            val newHubs = it.toSet().filter { hub -> !cachedHubs.toSet().contains(hub) }
            if (newHubs.isNotEmpty()) {
                if (isOwned) cacheDataSource.updateOwnHubs(newHubs)
                else cacheDataSource.updateSharedHubs(newHubs)
            }
            emit(NetworkResult.Success(it))
        }
        remoteHubs.onError { emit(NetworkResult.Error(it)) }
    }

    override suspend fun addItemToHub(hubItem: HubItem): NetworkResult<Unit, NetworkError> {
        val remoteResult = remoteDataSource.addItemToHub(hubItem)
        remoteResult.onSuccess { cacheDataSource.addItem(hubItem) }
        return remoteResult
    }

    override fun getItemsFromHub(hubId: Int): Flow<NetworkResult<List<HubItem>, NetworkError>> = flow {
        val cachedItems = cacheDataSource.getItemsFromHub(hubId)
        if (cachedItems.isNotEmpty()) emit(NetworkResult.Success(cachedItems))

        val remoteItems = remoteDataSource.getItemsFromHub(hubId)
        remoteItems.onSuccess {
            val newItems = it.toSet().filter { item -> !cachedItems.toSet().contains(item) }
            if (newItems.isNotEmpty()) cacheDataSource.addItems(newItems)
            emit(NetworkResult.Success(it))
        }
        remoteItems.onError { emit(NetworkResult.Error(it)) }
    }
}