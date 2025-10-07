package com.example.fairthread.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class StoresViewModel(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) : ViewModel() {

    private val _stores = MutableStateFlow<List<Map<String, Any>>>(emptyList())
    val stores: StateFlow<List<Map<String, Any>>> = _stores

    fun loadStores() {
        viewModelScope.launch {
            try {
                val snapshot = firestore.collection("stores").get().await()
                _stores.value = snapshot.documents.mapNotNull { it.data }
            } catch (e: Exception) {
                // Handle error if needed
                _stores.value = emptyList()
            }
        }
    }
}