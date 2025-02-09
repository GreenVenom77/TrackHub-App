package com.skewnexus.trackhub.navigation.util

import com.greenvenom.navigation.NavigationTarget
import com.skewnexus.trackhub.navigation.routes.Screen
import com.skewnexus.trackhub.navigation.routes.SubGraph
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import kotlinx.serialization.serializer

val navigationJson = Json {
    serializersModule = SerializersModule {
        polymorphic(NavigationTarget::class) {
            subclass(Screen.Splash::class)
            subclass(Screen.Login::class)
            subclass(Screen.Register::class)
            subclass(Screen.VerifyEmail::class)
            subclass(Screen.OTP::class)
            subclass(Screen.NewPassword::class)
            subclass(Screen.Home::class)
            subclass(Screen.Activity::class)
            subclass(Screen.Profile::class)

            subclass(SubGraph.Auth::class)
            subclass(SubGraph.Main::class)
        }
    }
}