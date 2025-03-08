package com.trackhub.feat_hub.presentation.hub_details.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import com.trackhub.core_hub.domain.models.Hub
import com.trackhub.feat_hub.R
import com.trackhub.feat_hub.presentation.components.HubBottomSheet
import com.trackhub.feat_hub.presentation.components.ItemListCard
import com.trackhub.feat_hub.presentation.hub_details.HubDetailsAction
import com.trackhub.feat_hub.presentation.hub_details.HubDetailsState
import com.trackhub.feat_hub.presentation.hub_details.HubDetailsViewModel
import com.trackhub.feat_hub.presentation.models.toHubItemUI
import com.trackhub.feat_hub.presentation.models.toHubUI

@Composable
fun HubDetailsScreen(
    hubId: String,
    onPhysicalBack: () -> Unit,
    onHubDeletion: () -> Unit,
    onHubRetrieval: (Hub) -> Unit,
    hubBottomSheetState: Boolean,
    onHubBottomSheetDismiss: () -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    BaseScreen<HubDetailsViewModel> { viewModel ->
        val hubDetailsState by viewModel.hubDetailsState.collectAsStateWithLifecycle()

        DisposableEffect(lifecycleOwner) {
            val observer = LifecycleEventObserver { _, event ->
                when (event) {
                    Lifecycle.Event.ON_STOP -> {
                        viewModel.hubDetailsAction(HubDetailsAction.StopCollectingHubItems)
                        onPhysicalBack()
                    }
                    Lifecycle.Event.ON_START -> {
                        viewModel.hubDetailsAction(HubDetailsAction.StartCollectingHubItems(hubId))
                    }
                    else -> { /* Ignore other events */ }
                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)
            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
                viewModel.hubDetailsAction(HubDetailsAction.ClearState)
            }
        }

        HubDetailsContent(
            hubDetailsState = hubDetailsState,
            hubDetailsAction = viewModel::hubDetailsAction,
            baseAction = viewModel::baseAction,
            hubBottomSheetState = hubBottomSheetState,
            onHubBottomSheetDismiss = onHubBottomSheetDismiss,
            onHubDeletion = onHubDeletion,
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        )

        hubDetailsState.hub?.let { onHubRetrieval(it) }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HubDetailsContent(
    hubDetailsState: HubDetailsState,
    hubDetailsAction: (HubDetailsAction) -> Unit,
    baseAction: (BaseAction) -> Unit,
    hubBottomSheetState: Boolean,
    onHubBottomSheetDismiss: () -> Unit,
    onHubDeletion: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState()
    val hubItemsResult = hubDetailsState.hubItemsResult
    var isItemSheetShown by rememberSaveable { mutableStateOf(false) }

    hubItemsResult
        ?.onSuccess { baseAction(BaseAction.HideLoading) }
        ?.onError { error ->
            baseAction(BaseAction.HideLoading)
            baseAction(BaseAction.ShowErrorMessage(
                error.errorType?.toString(context) ?: context.getString(R.string.something_went_wrong)))
        }

    hubDetailsState.operationResult
        ?.onSuccess { baseAction(BaseAction.HideLoading) }
        ?.onError { error ->
            baseAction(BaseAction.HideLoading)
            baseAction(BaseAction.ShowErrorMessage(
                error.errorType?.toString(context) ?: context.getString(R.string.something_went_wrong)))
        }

    hubDetailsState.hubDeletionResult
        ?.onSuccess {
            baseAction(BaseAction.HideLoading)
            onHubDeletion()
        }
        ?.onError { error ->
            baseAction(BaseAction.HideLoading)
            baseAction(BaseAction.ShowErrorMessage(
                error.errorType?.toString(context) ?: context.getString(R.string.something_went_wrong)))
        }

    hubDetailsState.itemDeletionResult
        ?.onSuccess {
            baseAction(BaseAction.HideLoading)
            isItemSheetShown = false
        }
        ?.onError { error ->
            baseAction(BaseAction.HideLoading)
            baseAction(BaseAction.ShowErrorMessage(
                error.errorType?.toString(context) ?: context.getString(R.string.something_went_wrong)))
        }

    Box(modifier = modifier.fillMaxSize()) {
        hubItemsResult?.onSuccess { hubItems ->
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 128.dp),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(
                    items = hubItems,
                    key = { hubItem -> hubItem.id }
                ) { hubItem ->
                    ItemListCard(
                        hubItem = hubItem.toHubItemUI(),
                        onClick = { isItemSheetShown = true }
                    )
                }
            }
        }

        FloatingButton(
            onClick = { isItemSheetShown = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(22.dp)
                .size(64.dp)
        )

        if (hubBottomSheetState) {
            hubDetailsState.hub?.let { hub ->
                HubBottomSheet(
                    hub = hub.toHubUI(),
                    sheetState = sheetState,
                    onDismiss = { onHubBottomSheetDismiss() },
                    isEdit = true,
                    onEdit = { hubName, hubDescription ->
                        baseAction(BaseAction.ShowLoading)
                        hubDetailsAction(HubDetailsAction.UpdateHub(hubName, hubDescription))
                    },
                    onDelete = { hubId ->
                        baseAction(BaseAction.ShowLoading)
                        hubDetailsAction(HubDetailsAction.DeleteHub(hubId))
                    }
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun HubDetailsContentPreview() {
    HubDetailsContent(
        hubDetailsState = HubDetailsState(),
        hubDetailsAction = {  },
        baseAction = {  },
        hubBottomSheetState = false,
        onHubBottomSheetDismiss = {  },
        onHubDeletion = {  },
    )
}