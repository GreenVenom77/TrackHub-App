package com.greenvenom.navigation.utils

import androidx.compose.runtime.saveable.Saver
import com.greenvenom.navigation.SubGraph
import kotlinx.serialization.json.Json

val subGraphSaver = Saver<SubGraph, String>(
    save = { graph -> Json.encodeToString(SubGraph.serializer(), graph) },
    restore = { json -> Json.decodeFromString(SubGraph.serializer(), json) }
)