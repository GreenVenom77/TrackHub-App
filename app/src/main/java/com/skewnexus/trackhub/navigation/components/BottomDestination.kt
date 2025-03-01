package com.skewnexus.trackhub.navigation.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.greenvenom.navigation.domain.NavigationTarget
import com.skewnexus.trackhub.R
import com.skewnexus.trackhub.navigation.routes.Screen

enum class BottomDestination(
    @DrawableRes val icon: Int,
    @StringRes val label: Int,
    val target: NavigationTarget,
) {
    MyHubs(
        icon = R.drawable.home_ic,
        label = R.string.my_hubs,
        target = Screen.MyHubs
    ),
    SharedHubs(
        icon = R.drawable.others_hubs_ic,
        label = R.string.shared_hubs,
        target = Screen.SharedHubs
    ),
    Activity(
        icon = R.drawable.activity_ic,
        label = R.string.activity,
        target = Screen.Activity
    ),
}