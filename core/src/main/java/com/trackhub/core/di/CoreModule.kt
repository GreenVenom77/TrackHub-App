package com.trackhub.core.di

import androidx.room.Room
import com.greenvenom.auth.domain.repository.AuthRepository
import com.trackhub.core.data.cache.RoomDataSource
import com.trackhub.core.data.cache.db.TrackHubDatabase
import com.trackhub.core.data.features.auth.AuthRepositoryImpl
import com.trackhub.core.data.features.hub.HubRepositoryImpl
import com.trackhub.core.data.remote.SupabaseDataSource
import com.trackhub.core.domain.cache.CacheDataSource
import com.trackhub.core.domain.remote.RemoteDataSource
import com.trackhub.hub.domain.repository.HubRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val coreModule = module {
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