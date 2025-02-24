package com.trackhub.data.datasource.di

import com.trackhub.data.datasource.cache.RoomDataSource
import com.trackhub.domain.datasource.remote.RemoteDataSource
import com.trackhub.data.datasource.remote.SupabaseDataSource
import com.trackhub.domain.datasource.cache.CacheDataSource
import org.koin.dsl.module

val dataSourceModule = module {
    single<RemoteDataSource>() {
        SupabaseDataSource(
            supabaseClient = get()
        )
    }

    single<CacheDataSource>() {
        RoomDataSource()
    }
}