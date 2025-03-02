package com.trackhub.hub.di

import com.trackhub.hub.presentation.hub_details.HubDetailsViewModel
import com.trackhub.hub.presentation.hub_list.HubListViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val hubModule = module {
    singleOf(::HubListViewModel)
    singleOf(::HubDetailsViewModel)
}