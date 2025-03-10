package com.trackhub.feat_navigation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trackhub.feat_navigation.R
import com.greenvenom.core_ui.theme.AppTheme

@Composable
fun TopAppBar(
    isVisible: Boolean,
    isSideDestination: Boolean,
    isActionEnabled: Boolean,
    navigateBack: () -> Unit,
    logo: Painter = painterResource(R.drawable.logo),
    title: String = stringResource(R.string.app_name),
    action: @Composable () -> Unit = {}
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(initialOffsetY = { -it }),
        exit = slideOutVertically(targetOffsetY = { -it }),
        content = {
            TopBarContent(
                isSideDestination = isSideDestination,
                isActionEnabled = isActionEnabled,
                navigateBack = navigateBack,
                logo = logo,
                title = title,
                action = action
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBarContent(
    isSideDestination: Boolean,
    isActionEnabled: Boolean,
    navigateBack: () -> Unit,
    logo: Painter,
    title: String,
    action: @Composable () -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme

    TopAppBar(
        navigationIcon = {
            AnimatedVisibility(
                visible = isSideDestination,
                content = {
                    IconButton(onClick = { navigateBack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.short_back_arrow),
                            contentDescription = stringResource(R.string.back_button),
                            tint = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            )
        },
        actions = {
            AnimatedVisibility(
                visible = isActionEnabled,
                content = { action() }
            )
        },
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Logo
                Box(
                    modifier = Modifier
                        .size(40.dp)
                ) {
                    Image(
                        painter = logo,
                        contentDescription = "Logo",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                // Title
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    color = colorScheme.onBackground
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorScheme.surface,
            titleContentColor = colorScheme.onSurface
        )
    )
}

@Preview
@Composable
private fun TopAppBarPreview() {
    AppTheme {
        TopBarContent(
            isSideDestination = true,
            isActionEnabled = true,
            navigateBack = {  },
            logo = painterResource(R.drawable.logo),
            title = "Test",
            action = { IconButton(onClick = {  }) {
                Icon(
                    painter = painterResource(id = R.drawable.short_back_arrow),
                    contentDescription = stringResource(R.string.back_button),
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.fillMaxSize()
                )
            } }
        )
    }
}