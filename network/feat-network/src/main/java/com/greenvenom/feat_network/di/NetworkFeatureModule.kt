package com.greenvenom.feat_network.di

import androidx.room.Room
import com.greenvenom.core_auth.domain.repository.AuthRepository
import com.greenvenom.feat_network.data.cache.RoomDataSource
import com.greenvenom.feat_network.data.cache.db.TrackHubDatabase
import com.greenvenom.feat_network.data.features.auth.AuthRepositoryImpl
import com.greenvenom.feat_network.data.features.hub.HubRepositoryImpl
import com.greenvenom.feat_network.data.remote.SupabaseDataSource
import com.greenvenom.feat_network.domain.cache.CacheDataSource
import com.greenvenom.feat_network.domain.remote.RemoteDataSource
import com.greenvenom.core_hub.domain.repository.HubRepository
import com.greenvenom.feat_network.data.remote.repository.SupabaseSessionRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val networkFeatureModule = module {
    single<SupabaseSessionRepository>(createdAtStart = true) {
        SupabaseSessionRepository(supabaseClient = get())
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