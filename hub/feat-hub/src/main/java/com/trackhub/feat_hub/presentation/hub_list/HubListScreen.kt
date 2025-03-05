package com.trackhub.feat_hub.presentation.hub_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.greenvenom.core_network.data.onError
import com.greenvenom.core_network.data.onSuccess
import com.greenvenom.core_network.utils.toString
import com.greenvenom.core_ui.components.CustomButton
import com.greenvenom.core_ui.components.CustomMultilineTextField
import com.greenvenom.core_ui.components.FloatingButton
import com.greenvenom.core_ui.components.CustomTextField
import com.greenvenom.core_ui.presentation.BaseAction
import com.greenvenom.core_ui.presentation.BaseScreen
import com.trackhub.feat_hub.R
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

        HubListContent(
            showOwnedHubs = showOwnedHubs,
            hubListState = hubListState,
            hubListAction = viewModel::hubListAction,
            baseAction = viewModel::baseAction,
            navigateToHubDetails = navigateToHubDetails,
            modifier = Modifier.fillMaxSize()
        )
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_STOP) {
                onPhysicalBack()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
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
    var hubName by rememberSaveable { mutableStateOf("") }
    var hubDescription by rememberSaveable { mutableStateOf("") }

    hubsResult?.onError { error ->
        baseAction(BaseAction.ShowErrorMessage(
            error.errorType?.toString(context) ?: context.getString(R.string.something_went_wrong)))
    }

    Box(modifier = modifier) {
        if (showOwnedHubs) {
            FloatingButton(
                onClick = { isSheetShown = true },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(22.dp)
                    .size(68.dp)
            )
        }

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
                                modifier = Modifier.padding(6.dp)
                            )
                        }
                    }
                }
            }
        }

        if (isSheetShown) {
            ModalBottomSheet(
                sheetState = sheetState,
                onDismissRequest = { isSheetShown = false }
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    CustomTextField(
                        value = hubName,
                        label = stringResource(R.string.hub_name),
                        error = "",
                        onValueChange = { hubName = it },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    CustomMultilineTextField(
                        value = hubDescription,
                        hintText = stringResource(R.string.hub_description),
                        onValueChanged = { hubDescription = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.surfaceContainer)
                            .padding(12.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    CustomButton(
                        text = stringResource(R.string.add_hub),
                        onClick = { hubListAction(HubListAction.AddHub(hubName, hubDescription)) },
                    )
                }
            }
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