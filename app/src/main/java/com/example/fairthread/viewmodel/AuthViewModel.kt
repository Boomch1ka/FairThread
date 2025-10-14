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


    fun testManualEmailValidation() {
        viewModelScope.launch {
            try {
                val url = "https://emailvalidation.abstractapi.com/v1/?api_key=050772f4799540208d369de7afe782a6&email=test@example.com"
                val result = withContext(Dispatchers.IO) {
                    URL(url).readText()
                }
                Log.d("EmailValidation", "Manual response: $result")
                Log.d("EmailValidation", "Button clicked")
            } catch (e: Exception) {
                Log.e("EmailValidation", "Manual call failed: ${e.message}")
            }
        }
    }

    fun validateEmailBeforeRegister(email: String, onResult: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            try {
                val response = NetworkModule.emailApi.validateEmail("050772f4799540208d369de7afe782a6", email.trim())

                val url = "https://emailvalidation.abstractapi.com/v1/?api_key=your_actual_api_key&email=test@example.com"
                Log.d("EmailValidation", "Manual URL: $url")
                //Log.d("EmailValidation", "Quality score: ${response.quality_score}")

                val isValidFormat = response.is_valid_format.value
                val isSmtpValid = response.is_smtp_valid == true
                val isDisposable = response.is_disposable_email == false
                val isRoleEmail = response.is_role_email == false
                val qualityScore = response.quality_score?.toDoubleOrNull() ?: 0.0

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