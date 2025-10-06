package com.example.fairthread.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ForgotPasswordViewModel : ViewModel() {
    var email by mutableStateOf("")

    fun sendResetLink() {
        // FirebaseAuth.getInstance().sendPasswordResetEmail(email)
    }
}