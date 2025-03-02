package com.trackhub.hub.presentation.hub_details.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.greenvenom.base.presentation.BaseAction
import com.greenvenom.base.presentation.BaseScreen
import com.trackhub.hub.presentation.hub_details.HubDetailsAction
import com.trackhub.hub.presentation.hub_details.HubDetailsState
import com.trackhub.hub.presentation.hub_details.HubDetailsViewModel

@Composable
fun HubDetailsScreen() {
    BaseScreen<HubDetailsViewModel> { viewModel ->
        val hubDetailsState by viewModel.hubDetailsState.collectAsStateWithLifecycle()

        HubDetailsContent(
            hubDetailsState = hubDetailsState,
            hubDetailsAction = viewModel::hubDetailsAction,
            baseAction = viewModel::baseAction
        )
    }

}

@Composable
fun HubDetailsContent(
    hubDetailsState: HubDetailsState,
    hubDetailsAction: (HubDetailsAction) -> Unit,
    baseAction: (BaseAction) -> Unit
) {

}