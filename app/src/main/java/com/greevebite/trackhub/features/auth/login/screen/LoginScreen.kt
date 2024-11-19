package com.greevebite.trackhub.features.auth.login.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.greevebite.trackhub.R
import com.greevebite.trackhub.core.model.OperationType
import com.greevebite.trackhub.core.model.SupabaseResponse
import com.greevebite.trackhub.core.theme.TrackHubTheme
import com.greevebite.trackhub.core.viewmodel.SupabaseViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(supabaseViewModel: SupabaseViewModel = koinViewModel()) {
    var showDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    val loginState = supabaseViewModel.loginOperationsState.collectAsStateWithLifecycle().value

    LaunchedEffect(loginState) {
        when (loginState) {
            is SupabaseResponse.Idle -> {
                Toast.makeText(context, "Idle state", Toast.LENGTH_SHORT).show()
            }
            is SupabaseResponse.Loading -> {
                Toast.makeText(context, "Loading state", Toast.LENGTH_SHORT).show()
            }
            is SupabaseResponse.Success -> {
                // Handle success, such as navigating to a new screen
                Toast.makeText(context, "Login successful!", Toast.LENGTH_SHORT).show()
                supabaseViewModel.resetAuthOperationsState(OperationType.Login)
            }
            is SupabaseResponse.Failure -> {
                errorMessage = loginState.error.message
                showDialog = true
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = stringResource(R.string.login_failure_title)) },
            text = { Text(text = errorMessage) },
            confirmButton = {
                Button(onClick = {
                    showDialog = false
                    supabaseViewModel.resetAuthOperationsState(OperationType.Login)
                }) {
                    Text(text = stringResource(R.string.ok))
                }
            },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        )
    }

    LoginScreenContent(
        email = email,
        password = password,
        onEmailChange = { email = it },
        onPasswordChange = { password = it },
        onLoginClick = { supabaseViewModel.loginUser(email, password) }
    )
}

@Composable
private fun LoginScreenContent(
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