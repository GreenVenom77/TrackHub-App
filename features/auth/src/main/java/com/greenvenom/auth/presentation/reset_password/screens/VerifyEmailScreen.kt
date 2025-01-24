package com.greenvenom.auth.presentation.reset_password.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.greenvenom.validation.domain.ValidationResult
import com.greenvenom.validation.util.toString
import com.greenvenom.auth.AuthState
import com.greenvenom.auth.R
import com.greenvenom.auth.component.AuthHeader
import com.greenvenom.auth.data.repository.AuthStateRepository
import com.greenvenom.auth.presentation.reset_password.ResetPasswordAction
import com.greenvenom.auth.presentation.reset_password.ResetPasswordViewModel
import com.greenvenom.auth.component.AuthCustomButton
import com.greenvenom.auth.component.AuthTextField
import com.greenvenom.ui.theme.AppTheme
import com.greenvenom.ui.theme.backgroundLight
import com.greenvenom.ui.theme.onBackgroundLight
import org.koin.compose.koinInject

@Composable
fun VerifyEmailScreen(
    navigateBack: () -> Unit,
    navigateToOtpScreen: () -> Unit
) {
    val resetPasswordViewModel: ResetPasswordViewModel = koinInject()
    val authStateRepository: AuthStateRepository = koinInject()
    val authState by authStateRepository.authState.collectAsStateWithLifecycle()

    VerifyEmailContent(
        state = authState,
        action = resetPasswordViewModel::resetPasswordAction,
        navigateToOtpScreen = navigateToOtpScreen,
        navigateBack = navigateBack
    )
}

@Composable
private fun VerifyEmailContent(
    state: AuthState,
    action: (ResetPasswordAction) -> Unit,
    navigateToOtpScreen: () -> Unit,
    navigateBack: () -> Unit,
) {
    val context = LocalContext.current
    var email by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = backgroundLight)
    ) {
        AuthHeader(
            title = stringResource(R.string.enter_your_email),
            navigateBack = navigateBack,
            isLoginScreen = false
        )
        Column(
            modifier = Modifier
                .padding(18.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(13.dp) // Adds spacing between items
        ) {
            Text(
                text = stringResource(R.string.email),
                color = onBackgroundLight
            )
            AuthTextField(
                value = email,
                onValueChange = {
                    email = it
                    action(ResetPasswordAction.UpdateEmail(email))
                },
                label = stringResource(R.string.enter_your_email),
                error = if (state.emailValidity is ValidationResult.Error) state.emailValidity.error.toString(context) else "",
            )
            Spacer(modifier = Modifier.height(20.dp))
            AuthCustomButton(
                text = stringResource(R.string.next),
                enabled = state.emailValidity is ValidationResult.Success,
                onClick = {
                    navigateToOtpScreen()
                }
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun VerifyEmailContentPreview() {
    AppTheme {
        VerifyEmailContent(
            state = AuthState(),
            action = { },
            navigateToOtpScreen = { },
            navigateBack = { }
        )
    }
}