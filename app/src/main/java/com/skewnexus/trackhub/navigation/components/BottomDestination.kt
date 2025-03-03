package com.skewnexus.trackhub.navigation.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.greenvenom.navigation.domain.NavigationTarget
import com.skewnexus.trackhub.R
import com.trackhub.core.navigation.routes.Screen

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
        icon = R.drawable.shared_ic,
        label = R.string.shared_hubs,
        target = Screen.SharedHubs
    ),
    Activity(
        icon = R.drawable.activity_ic,
        label = R.string.activity,
        target = Screen.Activity
    ),
    More(
        icon = R.drawable.more_ic,
        label = R.string.more,
        target = Screen.More
    );
}