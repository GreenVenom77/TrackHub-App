package com.greenvenom.feat_menu.presentation

interface MenuAction {
    data object ChangeLanguage: MenuAction
    data object ChangeTheme: MenuAction
    data object Logout: MenuAction
}