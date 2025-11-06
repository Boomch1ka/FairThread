package com.example.fairthread.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fairthread.data.local.LocalSettingsRepository
import com.example.fairthread.data.local.SettingsEntity
import com.example.fairthread.data.repository.FirestoreRepository
import com.example.fairthread.utils.NetworkUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(
    application: Application,
    private val repo: FirestoreRepository = FirestoreRepository()
) : AndroidViewModel(application) {

    private val localRepo = LocalSettingsRepository(application)
    private val _settings = MutableStateFlow<Map<String, Any>?>(null)
    val settings: StateFlow<Map<String, Any>?> = _settings

    fun saveSettings(uid: String, username: String, email: String, password: String) {
        viewModelScope.launch {
            val updated = mapOf("username" to username, "email" to email, "password" to password)
            repo.updateUserSettings(uid, updated)
            localRepo.saveSettings(SettingsEntity(uid, username, email, password))
            loadSettings(uid)
        }
    }


    fun loadSettings(uid: String) {
        viewModelScope.launch {
            val context = getApplication<Application>()
            val isOnline = NetworkUtils.isOnline(context)

            if (isOnline) {
                val remote = repo.getUserSettings(uid)
                _settings.value = remote
                val entity = SettingsEntity(
                    uid = uid,
                    username = remote["username"]?.toString() ?: "",
                    email = remote["email"]?.toString() ?: "",
                    password = remote["password"]?.toString() ?: ""
                )
                localRepo.saveSettings(entity)
            } else {
                val local = localRepo.getSettings(uid)
                _settings.value = local?.let {
                    mapOf("username" to it.username, "email" to it.email, "password" to it.password)
                }
            }
        }
    }

    suspend fun deleteUserAccount(
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
        localRepo.deleteSettings(uid)
    }

}