package com.greenvenom.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface SubGraph {
    @Serializable
    data object Auth: SubGraph

    @Serializable
    data object Main: SubGraph
}