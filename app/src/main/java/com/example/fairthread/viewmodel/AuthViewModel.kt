package com.example.fairthread.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fairthread.data.repository.AuthRepository
import com.example.fairthread.di.NetworkModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class AuthViewModel(private val repo: AuthRepository = AuthRepository()) : ViewModel() {

    private val _authState = MutableStateFlow<Result<Unit>?>(null)
    val authState: StateFlow<Result<Unit>?> = _authState

    fun register(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = repo.register(email, password)
        }
    }

    fun testManualEmailReputation() {
        viewModelScope.launch {
            try {
                val url = "https://emailreputation.abstractapi.com/v1/?api_key=050772f4799540208d369de7afe782a6&email=test@example.com"
                val result = withContext(Dispatchers.IO) { URL(url).readText()
                }
                Log.d("EmailReputation", "Manual response: $result")
                Log.d("EmailReputation", "Button clicked")
            } catch (e: Exception) {
                Log.e("EmailReputation", "Manual call failed: ${e.message}")
            }
        }
    }

    fun validateEmailBeforeRegister(email: String, onResult: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            try {
                val response = NetworkModule.emailApi.validateEmail("050772f4799540208d369de7afe782a6", email)
                Log.d("EmailValidation", "Parsed response: $response")

                val isValidFormat = response.email_deliverability?.is_format_valid == true
                val isSmtpValid = response.email_deliverability?.is_smtp_valid == true
                val isDisposable = response.email_quality?.is_disposable == false
                val isRoleEmail = response.email_quality?.is_role == false
                val qualityScore = response.email_quality?.score?.toDoubleOrNull() ?: 0.0

                val isHighQuality = qualityScore >= 0.7

                val isValid = isValidFormat && isSmtpValid && isDisposable && isRoleEmail && isHighQuality

                onResult(isValid, if (isValid) null else "Email failed quality check. Try a different address.")
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