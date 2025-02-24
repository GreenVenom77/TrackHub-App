package com.greenvenom.base.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.greenvenom.base.components.ErrorDialog
import com.greenvenom.base.components.LoadingDialog
import org.koin.androidx.compose.koinViewModel

@Composable
inline fun<reified VM: BaseViewModel> BaseScreen(
    content: @Composable (viewModel: VM) -> Unit,
) {
    val viewModel: VM = koinViewModel()
    val baseState = viewModel.baseState.collectAsState()

    content(viewModel)
    LoadingDialog(
        isLoading = baseState.value.isLoading
    )
    ErrorDialog(
        errorMessage = baseState.value.errorMessage,
        dismissAction = viewModel::hideErrorMessage
    )
}