package com.skewnexus.trackhub.di

import androidx.room.Room
import com.greenvenom.auth.domain.repository.AuthRepository
import com.skewnexus.trackhub.data.cache.RoomDataSource
import com.skewnexus.trackhub.data.cache.db.TrackHubDatabase
import com.skewnexus.trackhub.data.features.auth.AuthRepositoryImpl
import com.skewnexus.trackhub.data.features.hub.HubRepositoryImpl
import com.skewnexus.trackhub.data.remote.SupabaseDataSource
import com.skewnexus.trackhub.domain.cache.CacheDataSource
import com.skewnexus.trackhub.domain.remote.RemoteDataSource
import com.skewnexus.trackhub.navigation.utils.SessionDestinationHandler
import com.trackhub.hub.domain.repository.HubRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single {
        SessionDestinationHandler(
            navigationStateRepository = get(),
            sessionStateRepository = get()
        )
    }

    single<AuthRepository> {
        AuthRepositoryImpl(remoteDataSource = get())
    }

    single<HubRepository> {
        HubRepositoryImpl(
            remoteDataSource = get(),
            cacheDataSource = get()
        )
    }

    single<RemoteDataSource>() {
        SupabaseDataSource(
            supabaseClient = get()
        )
    }

    single<CacheDataSource>() {
        RoomDataSource(hubDao = get())
    }

    single {
        get<TrackHubDatabase>().hubDao
    }

    single {
        Room.databaseBuilder(
            androidContext(),
            TrackHubDatabase::class.java,
            name = "trackhub.db"
        ).build()
    }
}