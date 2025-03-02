package com.trackhub.core.data.features.hub

import com.greenvenom.networking.data.NetworkError
import com.greenvenom.networking.data.NetworkResult
import com.greenvenom.networking.data.onError
import com.greenvenom.networking.data.onSuccess
import com.trackhub.core.domain.cache.CacheDataSource
import com.trackhub.core.domain.remote.RemoteDataSource
import com.trackhub.hub.domain.models.Hub
import com.trackhub.hub.domain.models.HubItem
import com.trackhub.hub.domain.repository.HubRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach

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
        var cachedHubs: List<Hub> = emptyList()

        if (isOwned) {
            cacheDataSource.getOwnHubs().collect { cachedOwnedHubs ->
                cachedHubs = cachedOwnedHubs
                emit(NetworkResult.Success(cachedOwnedHubs))
            }
        } else {
            cacheDataSource.getSharedHubs().collect { cachedSharedHubs ->
                cachedHubs = cachedSharedHubs
                emit(NetworkResult.Success(cachedSharedHubs))
            }
        }

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

    override fun getItemsFromHub(hubId: String): Flow<NetworkResult<List<HubItem>, NetworkError>> = flow {
        val cachedItems = cacheDataSource.getItemsFromHub(hubId)
        if (cachedItems.isNotEmpty()) emit(NetworkResult.Success(cachedItems))

        remoteDataSource.getItemsFromHub(hubId).onEach { remoteItems ->
            remoteItems.onSuccess { items ->
                emit(NetworkResult.Success(items))
                val newItems = items.toSet().filter { item -> !cachedItems.toSet().contains(item) }
                if (newItems.isNotEmpty()) cacheDataSource.addItems(newItems)
            }
            remoteItems.onError { emit(NetworkResult.Error(it)) }
        }
    }
}