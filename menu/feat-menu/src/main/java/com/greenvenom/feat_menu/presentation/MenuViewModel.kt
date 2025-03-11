package com.greenvenom.feat_menu.presentation

import androidx.lifecycle.viewModelScope
import com.greenvenom.core_menu.domain.MenuRepository
import com.greenvenom.core_ui.presentation.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MenuViewModel(
    private val menuRepository: MenuRepository
): BaseViewModel() {
    private val _menuState = MutableStateFlow(MenuState())
    val menuState = _menuState.asStateFlow()

    fun menuAction(action: MenuAction) {
        when (action) {
            is MenuAction.ChangeTheme -> changeTheme()
            is MenuAction.ChangeLanguage -> changeLanguage()
            is MenuAction.Logout -> logout()
        }
    }

    private fun changeTheme() {
        _menuState.update {
            it.copy(
                isDarkTheme = !it.isDarkTheme
            )
        }
    }

    private fun changeLanguage() {
        _menuState.update {
            it.copy(
                isArabic = !it.isArabic
            )
        }
    }

    private fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = menuRepository.logout()

            _menuState.update {
                it.copy(
                    logoutResult = result
                )
            }
        }
    }
}