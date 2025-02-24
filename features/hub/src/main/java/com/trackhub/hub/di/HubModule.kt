package com.trackhub.hub.di

import com.trackhub.data.datasource.cache.db.TrackHubDatabase
import com.trackhub.hub.data.repository.HubRepositoryImpl
import com.trackhub.hub.domain.repository.HubRepository
import org.koin.dsl.module

val hubModule = module {
    single<HubRepository> {
        HubRepositoryImpl(
            remoteDataSource = get(),
            cacheDataSource = get()
        )
    }
}