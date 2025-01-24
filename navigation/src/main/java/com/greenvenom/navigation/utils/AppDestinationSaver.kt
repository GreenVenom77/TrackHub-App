package com.greenvenom.navigation.utils

import androidx.compose.runtime.saveable.Saver
import com.greenvenom.navigation.AppDestination
import kotlinx.serialization.json.Json

val appDestinationSaver = Saver<AppDestination, String>(
    save = { destination -> Json.encodeToString(AppDestination.serializer(), destination) },
    restore = { json -> Json.decodeFromString(AppDestination.serializer(), json) }
)