package com.trackhub.feat_hub.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.greenvenom.core_ui.components.CustomButton
import com.greenvenom.core_ui.components.CustomMultilineTextField
import com.greenvenom.core_ui.components.CustomTextField
import com.trackhub.feat_hub.R
import com.trackhub.feat_hub.presentation.models.HubUI

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HubBottomSheet(
    sheetState: SheetState,
    onDismiss: () -> Unit,
    isEdit: Boolean,
    modifier: Modifier = Modifier,
    hub: HubUI? = null,
    onAdd: (String, String) -> Unit = {_,_ ->},
    onEdit: (String, String, String) -> Unit = {_,_,_ ->},
    onDelete: (String) -> Unit = {},
) {
    HubSheetContent(
        sheetState = sheetState,
        onDismiss = onDismiss,
        isEdit = isEdit,
        modifier = modifier,
        hubID = hub?.id ?: "",
        hubName = hub?.name ?: "",
        hubDescription = hub?.description ?: "",
        onAdd = onAdd,
        onEdit = onEdit,
        onDelete = onDelete
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HubSheetContent(
    sheetState: SheetState,
    onDismiss: () -> Unit,
    isEdit: Boolean,
    modifier: Modifier = Modifier,
    hubID: String = "",
    hubName: String = "",
    hubDescription: String = "",
    onAdd: (String, String) -> Unit,
    onEdit: (String, String, String) -> Unit,
    onDelete: (String) -> Unit,
) {
    var newHubName by remember { mutableStateOf(hubName) }
    var newHubDescription by remember { mutableStateOf(hubDescription) }

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismiss,
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            if (isEdit) {
                CustomTextField(
                    value = hubID,
                    readOnly = true,
                    label = stringResource(R.string.hub_id),
                    error = "",
                    onValueChange = {  },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            CustomTextField(
                value = newHubName,
                label = stringResource(R.string.hub_name),
                error = "",
                onValueChange = { newHubName = it },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomMultilineTextField(
                value = newHubDescription,
                hintText = stringResource(R.string.hub_description),
                onValueChanged = { newHubDescription = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surfaceContainer)
                    .padding(12.dp)
            )
            Spacer(modifier = Modifier.height(40.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CustomButton(
                    text = stringResource(
                        if (isEdit) R.string.update_hub else R.string.add_hub
                    ),
                    onClick = {
                        if (isEdit) {
                            onEdit(hubID, newHubName, newHubDescription)
                        } else {
                            onAdd(newHubName, newHubDescription)
                        }
                    },
                    enabled = newHubName.isNotEmpty()
                )
                if (isEdit) {
                    CustomButton(
                        text = stringResource(R.string.delete_hub),
                        onClick = {
                            onDelete(hubID)
                        },
                        colors = ButtonDefaults.filledTonalButtonColors(
                            containerColor = MaterialTheme.colorScheme.error
                        ),
                        enabled = hubID.isNotEmpty()
                    )
                }
            }
        }
    }
}