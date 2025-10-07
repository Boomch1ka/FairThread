package com.example.fairthread.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fairthread.data.repository.FirestoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val repo: FirestoreRepository = FirestoreRepository()
) : ViewModel() {

    private val _settings = MutableStateFlow<Map<String, Any>?>(null)
    val settings: StateFlow<Map<String, Any>?> = _settings

    fun saveSettings(uid: String, theme: String, notificationsEnabled: Boolean) {
        viewModelScope.launch {
            repo.saveUserSettings(uid, theme, notificationsEnabled)
            loadSettings(uid)
        }
    }

    fun loadSettings(uid: String) {
        viewModelScope.launch {
            _settings.value = repo.getUserSettings(uid)
        }
    }
}