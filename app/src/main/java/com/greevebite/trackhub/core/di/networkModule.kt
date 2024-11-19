package com.greevebite.trackhub.core.di

import com.greevebite.trackhub.core.data.remote.SupabaseClient
import com.greevebite.trackhub.core.viewmodel.SupabaseViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val networkModule = module {
    single<SupabaseClient> { SupabaseClient() }
    viewModel { SupabaseViewModel(get()) }
}