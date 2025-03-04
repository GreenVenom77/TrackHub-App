package com.skewnexus.trackhub.navigation.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.greenvenom.core_navigation.domain.NavigationTarget
import com.skewnexus.trackhub.R

enum class BottomDestination(
    @DrawableRes val icon: Int,
    @StringRes val label: Int,
    val target: NavigationTarget,
) {
    MyHubs(
        icon = R.drawable.home_ic,
        label = R.string.my_hubs,
        target = com.trackhub.feat_navigation.routes.Screen.MyHubs
    ),
    SharedHubs(
        icon = R.drawable.shared_ic,
        label = R.string.shared_hubs,
        target = com.trackhub.feat_navigation.routes.Screen.SharedHubs
    ),
    Activity(
        icon = R.drawable.activity_ic,
        label = R.string.activity,
        target = com.trackhub.feat_navigation.routes.Screen.Activity
    ),
    More(
        icon = R.drawable.more_ic,
        label = R.string.more,
        target = com.trackhub.feat_navigation.routes.Screen.More
    );
}