package com.skewnexus.trackhub.navigation.routes

import com.greenvenom.navigation.NavigationTarget
import kotlinx.serialization.Serializable

@Serializable
sealed class SubGraph: NavigationTarget() {
    @Serializable
    data object Auth: SubGraph()

    @Serializable
    data object Main: SubGraph()
}