package com.trackhub.feat_hub.presentation.hub_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.greenvenom.core_network.data.onError
import com.greenvenom.core_network.data.onSuccess
import com.greenvenom.core_network.utils.toString
import com.greenvenom.core_ui.components.FloatingButton
import com.greenvenom.core_ui.presentation.BaseAction
import com.greenvenom.core_ui.presentation.BaseScreen
import com.trackhub.feat_hub.R
import com.trackhub.feat_hub.presentation.components.HubBottomSheet
import com.trackhub.feat_hub.presentation.components.HubListCard
import com.trackhub.feat_hub.presentation.models.toHubUI

@Composable
fun HubListScreen(
    showOwnedHubs: Boolean,
    navigateToHubDetails: (String) -> Unit,
    onPhysicalBack: () -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    BaseScreen<HubListViewModel> { viewModel ->
        val hubListState by viewModel.hubListState.collectAsStateWithLifecycle()

        DisposableEffect(lifecycleOwner) {
            val observer = LifecycleEventObserver { _, event ->
                when (event) {
                    Lifecycle.Event.ON_STOP -> {
                        viewModel.hubListAction(HubListAction.StopCollectingHubs(showOwnedHubs))
                        onPhysicalBack()
                    }
                    Lifecycle.Event.ON_START -> {
                        viewModel.hubListAction(HubListAction.StartCollectingHubs(showOwnedHubs))
                    }
                    else -> { /* Ignore other events */ }
                }
            }

            lifecycleOwner.lifecycle.addObserver(observer)

            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }

        HubListContent(
            showOwnedHubs = showOwnedHubs,
            hubListState = hubListState,
            hubListAction = viewModel::hubListAction,
            baseAction = viewModel::baseAction,
            navigateToHubDetails = navigateToHubDetails,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HubListContent(
    showOwnedHubs: Boolean,
    hubListState: HubListState,
    hubListAction: (HubListAction) -> Unit,
    baseAction: (BaseAction) -> Unit,
    navigateToHubDetails: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState()
    val hubsResult = if (showOwnedHubs) hubListState.ownedHubsResult else hubListState.sharedHubsResult

    var isSheetShown by rememberSaveable { mutableStateOf(false) }

    hubsResult
        ?.onSuccess { baseAction(BaseAction.HideLoading) }
        ?.onError { error ->
            baseAction(BaseAction.HideLoading)
            baseAction(BaseAction.ShowErrorMessage(
                error.errorType?.toString(context) ?: context.getString(R.string.something_went_wrong)))
        }

    Box(modifier = modifier) {
        Column() {
            hubsResult?.onSuccess { hubs ->
                LazyColumn(
                    contentPadding = PaddingValues(8.dp),
                ) {
                    items(hubs) { hub ->
                        hub.toHubUI().let { hubUI ->
                            HubListCard(
                                hub = hubUI,
                                onClick = { navigateToHubDetails(hubUI.id) },
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                }
            }
        }

        if (isSheetShown) {
            HubBottomSheet(
                sheetState = sheetState,
                onDismiss = {
                    isSheetShown = false
                },
                isEdit = false,
                onAdd = { hubName, hubDescription ->
                    baseAction(BaseAction.ShowLoading)
                    hubListAction(HubListAction.AddHub(hubName, hubDescription))
                }
            )
        }

        if (showOwnedHubs) {
            FloatingButton(
                onClick = { isSheetShown = true },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(22.dp)
                    .size(64.dp)
            )
        }
    }
}

@Preview
@Composable
private fun HubListPreview() {
    HubListContent(
        showOwnedHubs = true,
        hubListState = HubListState(),
        hubListAction = {  },
        baseAction = {  },
        navigateToHubDetails = {  },
    )
}