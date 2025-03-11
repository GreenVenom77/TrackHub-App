package com.greenvenom.feat_menu.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.greenvenom.core_ui.presentation.BaseAction
import com.greenvenom.core_ui.presentation.BaseScreen

@Composable
fun MenuScreen(modifier: Modifier = Modifier) {
    BaseScreen<MenuViewModel> { viewmodel ->
        val menuState by viewmodel.menuState.collectAsStateWithLifecycle()

        MenuContent(
            menuState = menuState,
            menuAction = viewmodel::menuAction,
            baseAction = viewmodel::baseAction,
            modifier = modifier
        )
    }
}

@Composable
private fun MenuContent(
    menuState: MenuState,
    menuAction: (MenuAction) -> Unit,
    baseAction: (BaseAction) -> Unit,
    modifier: Modifier = Modifier
) {

}

@Preview
@Composable
private fun MenuContentPreview() {
    MenuContent(
        menuState = MenuState(),
        menuAction = {},
        baseAction = {}
    )
}