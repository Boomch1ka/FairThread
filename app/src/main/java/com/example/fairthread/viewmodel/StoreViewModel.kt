package com.example.fairthread.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class StoreViewModel(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) : ViewModel() {

    private val _store = MutableStateFlow<Map<String, Any>?>(null)
    val store: StateFlow<Map<String, Any>?> = _store

    private val _storeProducts = MutableStateFlow<List<Map<String, Any>>>(emptyList())
    val storeProducts: StateFlow<List<Map<String, Any>>> = _storeProducts

    fun loadStore(storeId: String) {
        viewModelScope.launch {
            try {
                val snapshot = firestore.collection("stores")
                    .document(storeId)
                    .get()
                    .await()
                _store.value = snapshot.data
            } catch (e: Exception) {
                _store.value = null
            }
        }
    }

    fun loadProductsForStore(storeId: String) {
        viewModelScope.launch {
            try {
                val snapshot = firestore.collection("products")
                    .whereEqualTo("storeId", storeId)
                    .get()
                    .await()
                _storeProducts.value = snapshot.documents.mapNotNull { it.data }
            } catch (e: Exception) {
                _storeProducts.value = emptyList()
            }
        }
    }
}