package com.example.fairthread.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fairthread.data.repository.FirestoreRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val repo: FirestoreRepository = FirestoreRepository()
) : ViewModel() {

    private val _settings = MutableStateFlow<Map<String, Any>?>(null)
    val settings: StateFlow<Map<String, Any>?> = _settings

    fun saveSettings(uid: String, username: String, email: String, password: String) {
        viewModelScope.launch {
            val updated = mapOf(
                "username" to username,
                "email" to email,
                "password" to password
            )
            repo.updateUserSettings(uid, updated)
            loadSettings(uid)
        }
    }


    fun loadSettings(uid: String) {
        viewModelScope.launch {
            _settings.value = repo.getUserSettings(uid)
        }
    }
}