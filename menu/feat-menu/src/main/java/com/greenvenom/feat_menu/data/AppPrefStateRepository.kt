package com.greenvenom.feat_menu.data

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import java.util.Locale

class AppPrefStateRepository() {
    private val _appPrefState = MutableStateFlow(AppPrefState())
    val appPrefState = _appPrefState.asStateFlow()

    private val Context.themeDataStore by preferencesDataStore(name = "theme_prefs")
    private val DARK_THEME_KEY = booleanPreferencesKey(name = "dark_theme")

    init {
        _appPrefState.update {
            it.copy(
                currentLanguageTag = getCurrentLanguage()
            )
        }
    }
    
    suspend fun changeTheme(context: Context, isDarkTheme: Boolean) {
        context.themeDataStore.edit { preferences ->
            preferences[DARK_THEME_KEY] = isDarkTheme
        }

        _appPrefState.update {
            it.copy(
                isDarkTheme = isDarkTheme
            )
        }
    }

    fun getThemePreference(context: Context): Flow<Boolean> =
        context.themeDataStore.data.map { preferences ->
            preferences[DARK_THEME_KEY] ?: false
        }

    fun changeLanguage(languageTag: String) {
            val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(languageTag)
            AppCompatDelegate.setApplicationLocales(appLocale)

        _appPrefState.update {
            it.copy(
                currentLanguageTag = languageTag
            )
        }
    }

    fun getCurrentLanguage(): String {
        val locales: LocaleListCompat = AppCompatDelegate.getApplicationLocales()

        return if (locales.isEmpty) {
            Locale.ENGLISH.toLanguageTag()
        } else {
            locales[0]?.toLanguageTag() as String
        }
    }
}