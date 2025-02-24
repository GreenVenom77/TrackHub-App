package com.trackhub.data.datasource.di

import androidx.room.Room
import com.trackhub.data.datasource.cache.RoomDataSource
import com.trackhub.data.datasource.cache.db.TrackHubDatabase
import com.trackhub.domain.remote.RemoteDataSource
import com.trackhub.data.datasource.remote.SupabaseDataSource
import com.trackhub.domain.cache.CacheDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataSourceModule = module {
    single<RemoteDataSource>() {
        SupabaseDataSource(
            supabaseClient = get()
        )
    }

    single {
        Room.databaseBuilder(
            androidContext(),
            TrackHubDatabase::class.java,
            name = "trackhub.db"
        ).build()
    }

    single {
        get<TrackHubDatabase>().hubDao
    }

    single<CacheDataSource>() {
        RoomDataSource(hubDao = get())
    }
}