package com.example.fairthread.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fairthread.data.repository.FirestoreRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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

    fun deleteUserAccount(
        uid: String,
        onComplete: (Boolean) -> Unit
    ) {
        val auth = FirebaseAuth.getInstance()
        val firestore = FirebaseFirestore.getInstance()

        val user = auth.currentUser
        user?.delete()?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Delete Firestore data
                firestore.collection("users").document(uid).delete()
                firestore.collection("cart").document(uid).delete()
                firestore.collection("orders").document(uid).delete()

                onComplete(true)
            } else {
                Log.e("DeleteUser", "Failed to delete user: ${task.exception}")
                onComplete(false)
            }
        }
    }
}