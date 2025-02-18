package com.skewnexus.trackhub.navigation.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.greenvenom.navigation.domain.NavigationTarget
import com.skewnexus.trackhub.R
import com.greenvenom.navigation.routes.Screen

enum class BottomDestination(
    @DrawableRes val icon: Int,
    @StringRes val label: Int,
    val target: NavigationTarget,
) {
    Home(
        icon = R.drawable.home_ic,
        label = R.string.hubs,
        target = Screen.Home
    ),
    Activity(
        icon = R.drawable.activity_ic,
        label = R.string.activity,
        target = Screen.Activity
    ),
    Profile(
        icon = R.drawable.person_ic,
        label = R.string.profile,
        target = Screen.Profile
    )
}