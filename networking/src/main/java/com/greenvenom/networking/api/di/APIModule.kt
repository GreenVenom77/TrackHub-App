package com.greenvenom.networking.api.di

import com.greenvenom.networking.api.utils.HttpClientFactory
import io.ktor.client.engine.cio.CIO
import org.koin.dsl.module

val apiModule = module {
    single { HttpClientFactory.create(CIO.create()) }
}