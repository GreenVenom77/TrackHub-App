package com.greenvenom.feat_hub.di

import com.greenvenom.feat_hub.presentation.hub_details.HubDetailsViewModel
import com.greenvenom.feat_hub.presentation.hub_list.HubListViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val hubFeatureModule = module {
    singleOf(::HubListViewModel)
    singleOf(::HubDetailsViewModel)
}