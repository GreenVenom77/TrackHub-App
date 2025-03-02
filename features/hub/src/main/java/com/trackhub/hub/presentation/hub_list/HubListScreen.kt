package com.trackhub.hub.presentation.hub_list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.greenvenom.base.presentation.BaseAction
import com.greenvenom.base.presentation.BaseScreen

@Composable
fun HubListScreen() {
    BaseScreen<HubListViewModel> { viewModel ->
        val hubListState by viewModel.hubListState.collectAsStateWithLifecycle()

        HubListContent(
            hubListState = hubListState,
            hubListAction = viewModel::hubListAction,
            baseAction = viewModel::baseAction,
            navigateToHubDetails = {  }
        )
    }
}

@Composable
fun HubListContent(
    hubListState: HubListState,
    hubListAction: (HubListAction) -> Unit,
    baseAction: (BaseAction) -> Unit,
    navigateToHubDetails: () -> Unit
) {

}