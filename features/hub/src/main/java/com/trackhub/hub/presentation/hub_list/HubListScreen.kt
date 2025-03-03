package com.trackhub.hub.presentation.hub_list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.greenvenom.base.presentation.BaseAction
import com.greenvenom.base.presentation.BaseScreen

@Composable
fun HubListScreen(
    showOwnedHubs: Boolean,
    navigateToHubDetails: (String) -> Unit
) {
    BaseScreen<HubListViewModel> { viewModel ->
        val hubListState by viewModel.hubListState.collectAsStateWithLifecycle()

        HubListContent(
            showOwnedHubs = showOwnedHubs,
            hubListState = hubListState,
            hubListAction = viewModel::hubListAction,
            baseAction = viewModel::baseAction,
            navigateToHubDetails = navigateToHubDetails
        )
    }
}

@Composable
fun HubListContent(
    showOwnedHubs: Boolean,
    hubListState: HubListState,
    hubListAction: (HubListAction) -> Unit,
    baseAction: (BaseAction) -> Unit,
    navigateToHubDetails: (String) -> Unit
) {

}