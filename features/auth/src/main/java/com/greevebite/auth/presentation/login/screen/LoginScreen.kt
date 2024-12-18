package com.greevebite.auth.presentation.login.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.greevebite.auth.R
import com.greevebite.auth.presentation.login.LoginAction
import com.greevebite.auth.presentation.login.LoginState
import com.greevebite.auth.presentation.login.viewmodel.LoginViewModel
import com.greevebite.auth.util.ValidationError
import com.greevebite.domain.util.Result
import com.greevebite.domain.util.onSuccess
import com.greevebite.ui.components.AuthCustomButton
import com.greevebite.ui.components.AuthTextField
import com.greevebite.ui.presentation.BaseScreen

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onSuccessLogin: () -> Unit
    ) {
    BaseScreen<LoginViewModel> { viewModel ->
        val loginState = viewModel.loginState.collectAsStateWithLifecycle()

        LoginScreenContent(
            state = loginState.value,
            onSuccessLogin = onSuccessLogin,
            loginAction = viewModel::loginAction,
            modifier = modifier
        )
    }
}

@Composable
private fun LoginScreenContent(
    state: LoginState,
    onSuccessLogin: () -> Unit,
    loginAction: (LoginAction) -> Unit,
    modifier: Modifier = Modifier
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    state.loginResult?.onSuccess { onSuccessLogin() }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Text(
            text = stringResource(R.string.login_header),
        )
        AuthTextField(
            value = email,
            onValueChange = {
                email = it
                loginAction(LoginAction.ValidateEmail(password))
            },
            label = stringResource(R.string.email),
            error = if(state.emailValidity is Result.Error<ValidationError>) state.emailValidity.error.toString() else "",
        )
        AuthTextField(
            value = password,
            onValueChange = {
                password = it
                loginAction(LoginAction.ValidatePassword(password))
            },
            label = stringResource(R.string.password),
            error = if(state.passwordValidity is Result.Error<ValidationError>) state.passwordValidity.error.toString() else "",
            isPasswordField = true,
        )
        AuthCustomButton(
            text = stringResource(R.string.login_btn),
            onClick = { loginAction(LoginAction.Login(email, password)) }
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun LoginScreenContentPreview() {
    LoginScreenContent(
        state = LoginState(),
        onSuccessLogin = {  },
        loginAction = {  }
    )
}