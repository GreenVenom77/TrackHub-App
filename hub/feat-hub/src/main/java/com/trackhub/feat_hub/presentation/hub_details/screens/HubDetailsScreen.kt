package com.trackhub.feat_hub.presentation.hub_details.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.greenvenom.core_ui.presentation.BaseAction
import com.greenvenom.core_ui.presentation.BaseScreen
import com.trackhub.feat_hub.presentation.hub_details.HubDetailsAction
import com.trackhub.feat_hub.presentation.hub_details.HubDetailsState
import com.trackhub.feat_hub.presentation.hub_details.HubDetailsViewModel

@Composable
fun HubDetailsScreen(
    hubId: String
) {
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