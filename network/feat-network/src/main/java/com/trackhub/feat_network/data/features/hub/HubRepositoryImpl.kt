package com.trackhub.feat_network.data.features.hub

import com.greenvenom.core_network.data.NetworkError
import com.greenvenom.core_network.data.NetworkResult
import com.greenvenom.core_network.data.map
import com.greenvenom.core_network.data.onError
import com.greenvenom.core_network.data.onSuccess
import com.trackhub.feat_network.domain.cache.CacheDataSource
import com.trackhub.feat_network.domain.remote.RemoteDataSource
import com.trackhub.core_hub.domain.models.Hub
import com.trackhub.core_hub.domain.models.HubItem
import com.trackhub.core_hub.domain.repository.HubRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.take

@OptIn(ExperimentalCoroutinesApi::class)
class HubRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val cacheDataSource: CacheDataSource
): HubRepository {
    private val refreshTrigger = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    private val refreshItemsTrigger = MutableSharedFlow<String>(extraBufferCapacity = 1)

    override fun refreshHubs() {
        refreshTrigger.tryEmit(Unit)
    }

    override fun refreshItems(hubId: String) {
        refreshItemsTrigger.tryEmit(hubId)
    }

    override suspend fun addHub(hub: Hub): NetworkResult<Unit, NetworkError> {
        val remoteResult = remoteDataSource.addHub(hub)
        remoteResult.onSuccess { returnedHub -> cacheDataSource.addHub(returnedHub) }
        return remoteResult.map {  }
    }

    override fun getHubs(isOwned: Boolean): Flow<NetworkResult<List<Hub>, NetworkError>> {
        return refreshTrigger
            .onStart { emit(Unit) } // Initial load
            .flatMapLatest { _ ->
                flow {
                    // Get the cache flow
                    val cacheFlow = if (isOwned) {
                        cacheDataSource.getOwnHubs()
                    } else {
                        cacheDataSource.getSharedHubs()
                    }

                    // Collect the first value from cache to emit immediately
                    var cachedHubs: List<Hub> = emptyList()
                    cacheFlow.take(1).collect {
                        cachedHubs = it
                        emit(NetworkResult.Success(it))
                    }

                    // Fetch remote data
                    val remoteHubs = if (isOwned) remoteDataSource.getOwnHubs() else remoteDataSource.getSharedHubs()

                    remoteHubs.onSuccess { fetchedHubs ->
                        val newHubs = fetchedHubs.filter { hub -> hub !in cachedHubs }
                        if (newHubs.isNotEmpty()) {
                            if (isOwned) cacheDataSource.updateOwnHubs(newHubs)
                            else cacheDataSource.updateSharedHubs(newHubs)
                        }

                        emit(NetworkResult.Success(fetchedHubs))
                    }

                    remoteHubs.onError { error ->
                        emit(NetworkResult.Error(error))
                    }

                    // Continue emitting from cache after the remote fetch
                    cacheFlow.drop(1).collect {
                        emit(NetworkResult.Success(it))
                    }
                }
            }
    }

    override suspend fun addItemToHub(hubItem: HubItem): NetworkResult<Unit, NetworkError> {
        val remoteResult = remoteDataSource.addItemToHub(hubItem)
        remoteResult.onSuccess { cacheDataSource.addItem(hubItem) }
        return remoteResult
    }

    override fun getItemsFromHub(hubId: String): Flow<NetworkResult<List<HubItem>, NetworkError>> {
        return flow {
            // First emit from cache immediately
            val cachedItems = cacheDataSource.getItemsFromHub(hubId)
            if (cachedItems.isNotEmpty()) {
                emit(NetworkResult.Success(cachedItems))
            }

            // Create a flow that will emit when initial load or refresh is triggered
            val triggerFlow = refreshItemsTrigger
                .filter { it == hubId }
                .onStart { emit(hubId) }

            // Process each trigger (initial or refresh)
            triggerFlow.collect { _ ->
                remoteDataSource.getItemsFromHub(hubId)
                    .collect { remoteItems ->
                        remoteItems.onSuccess { items ->
                            emit(NetworkResult.Success(items))

                            // Get fresh cached items for comparison
                            val currentCachedItems = cacheDataSource.getItemsFromHub(hubId)
                            val newItems = items.filter { item -> item !in currentCachedItems }

                            if (newItems.isNotEmpty()) {
                                cacheDataSource.addItems(newItems)
                            }
                        }

                        remoteItems.onError { error ->
                            emit(NetworkResult.Error(error))
                        }
                    }
            }
        }
    }
}