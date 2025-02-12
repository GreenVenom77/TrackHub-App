package com.skewnexus.trackhub.navigation.components

import androidx.annotation.DrawableRes
import com.greenvenom.navigation.domain.NavigationTarget
import com.skewnexus.trackhub.R
import com.greenvenom.navigation.routes.Screen

enum class BottomDestination(
    @DrawableRes val icon: Int,
    val label: String,
    val target: NavigationTarget,
) {
    Home(
        icon = R.drawable.home_ic,
        label = "Home",
        target = Screen.Home
    ),
    Activity(
        icon = R.drawable.activity_ic,
        label = "Activity",
        target = Screen.Activity
    ),
    Profile(
        icon = R.drawable.person_ic,
        label = "Profile",
        target = Screen.Profile
    )
}