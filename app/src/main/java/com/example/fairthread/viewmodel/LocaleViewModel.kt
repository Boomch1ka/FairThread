package com.example.fairthread.viewmodel

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Locale

class LocaleViewModel(application: Application) : AndroidViewModel(application) {

    private val sharedPreferences = application.getSharedPreferences("prefs", Context.MODE_PRIVATE)

    private val _locale = MutableStateFlow(getCurrentLocale())
    val locale: StateFlow<Locale> = _locale

    init {
        updateAppLocale(_locale.value)
    }

    fun setLocale(languageCode: String) {
        val newLocale = Locale(languageCode)
        sharedPreferences.edit().putString("language", languageCode).apply()
        _locale.value = newLocale
        updateAppLocale(newLocale)
    }

    private fun getCurrentLocale(): Locale {
        val languageCode = sharedPreferences.getString("language", "en") ?: "en"
        return Locale(languageCode)
    }

    private fun updateAppLocale(locale: Locale) {
        val appLocale = LocaleListCompat.create(locale)
        AppCompatDelegate.setApplicationLocales(appLocale)
    }
}
