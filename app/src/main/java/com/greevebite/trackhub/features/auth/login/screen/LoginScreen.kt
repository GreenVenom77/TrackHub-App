package com.greevebite.trackhub.features.auth.login.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.greevebite.trackhub.R
import com.greevebite.trackhub.core.theme.TrackHubTheme
import com.greevebite.trackhub.core.viewmodel.AppViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(appViewModel: AppViewModel = koinViewModel()) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    LoginScreenContent(
        email = email,
        password = password,
        onEmailChange = { email = it },
        onPasswordChange = { password = it },
        onLoginClick = { appViewModel.loginUser(email, password) }
    )
}

@Composable
fun LoginScreenContent(
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit
    ) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
        .fillMaxSize()
        .padding(10.dp)
    ) {
        Text(
            text = stringResource(R.string.login_header ),
        )
        TextField(
            value = email,
            onValueChange = onEmailChange,
            label = { Text(text = stringResource(R.string.email)) },
            singleLine = true,
            leadingIcon = {
                Icon(painterResource(R.drawable.baseline_email_24), contentDescription = null)
            }
        )
        TextField(
            value = password,
            onValueChange = onPasswordChange,
            label = { Text(text = stringResource(R.string.password)) },
            singleLine = true,
            leadingIcon = {
                Icon(painterResource(R.drawable.baseline_password_24), contentDescription = null)
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(
                    onClick = { passwordVisible = !passwordVisible },
                    content = {
                        val icon = if (passwordVisible) R.drawable.baseline_visibility_24
                        else R.drawable.baseline_visibility_off_24
                        Icon(painterResource(icon), contentDescription = null)
                    }
                )
            }
        )
        Button(onClick = onLoginClick) {
            Text(
                text = stringResource(R.string.login_btn),
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    TrackHubTheme {
        LoginScreenContent(
            email = "",
            password = "",
            onEmailChange = {  },
            onPasswordChange = {  },
            onLoginClick = {  }
        )
    }
}