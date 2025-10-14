package com.example.fairthread.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fairthread.data.repository.AuthRepository
import com.example.fairthread.di.NetworkModule
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

    fun validateEmailBeforeRegister(email: String, onResult: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            try {
                val response = NetworkModule.emailApi.validateEmail("050772f4799540208d369de7afe782a6", email)
                val isValid = response.is_valid_format.value &&
                        response.is_smtp_valid == true &&
                        response.is_disposable_email == false &&
                        response.is_role_email == false

                onResult(isValid, if (isValid) null else "Invalid or suspicious email address.")
            } catch (e: Exception) {
                onResult(false, "Validation failed: ${e.message}")
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = repo.login(email, password)
        }
    }

    suspend fun resetPassword(email: String) {
        _authState.value = repo.sendPasswordReset(email)
    }

    fun logout() {
        repo.logout()
    }
}