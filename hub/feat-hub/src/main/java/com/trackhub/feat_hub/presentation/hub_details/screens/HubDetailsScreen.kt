package com.trackhub.feat_hub.presentation.hub_details.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.greenvenom.core_ui.presentation.BaseAction
import com.greenvenom.core_ui.presentation.BaseScreen
import com.trackhub.core_hub.domain.models.Hub
import com.trackhub.feat_hub.presentation.hub_details.HubDetailsAction
import com.trackhub.feat_hub.presentation.hub_details.HubDetailsState
import com.trackhub.feat_hub.presentation.hub_details.HubDetailsViewModel

@Composable
fun HubDetailsScreen(
    hubId: String,
    onPhysicalBack: () -> Unit,
    onHubRetrieval: (Hub) -> Unit
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
            }
        }

        HubDetailsContent(
            hubDetailsState = hubDetailsState,
            hubDetailsAction = viewModel::hubDetailsAction,
            baseAction = viewModel::baseAction,
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        )

        hubDetailsState.hub?.let { onHubRetrieval(it) }
    }
}

@Composable
private fun HubDetailsContent(
    hubDetailsState: HubDetailsState,
    hubDetailsAction: (HubDetailsAction) -> Unit,
    baseAction: (BaseAction) -> Unit,
    modifier: Modifier = Modifier
) {

}