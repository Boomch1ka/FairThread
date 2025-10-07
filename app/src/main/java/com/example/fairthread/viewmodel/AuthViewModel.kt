package com.example.fairthread.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fairthread.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val repo: AuthRepository = AuthRepository()) : ViewModel() {

    private val _authState = MutableStateFlow<Result<Unit>?>(null)
    val authState: StateFlow<Result<Unit>?> = _authState

    fun register(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = repo.register(email, password)
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = repo.login(email, password)
        }
    }

    fun resetPassword(email: String) {
        _authState.value = repo.sendPasswordReset(email)
    }

    fun logout() {
        repo.logout()
    }
}