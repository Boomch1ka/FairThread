package com.example.fairthread.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fairthread.data.repository.AuthRepository
import com.example.fairthread.model.EmailValidationResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val repo: AuthRepository) : ViewModel() {

    private val _authState = MutableStateFlow<Result<Unit>?>(null)
    val authState: StateFlow<Result<Unit>?> = _authState

    private val _emailValidation = MutableStateFlow<Result<EmailValidationResponse>?>(null)
    val emailValidation: StateFlow<Result<EmailValidationResponse>?> = _emailValidation

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

    fun validateEmail(email: String) {
        viewModelScope.launch {
            _emailValidation.value = repo.validateEmail(email)
        }
    }

    fun logout() {
        repo.logout()
    }
}
