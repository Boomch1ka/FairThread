package com.example.fairthread.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class CatalogueViewModel(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) : ViewModel() {

    private val _categories = MutableStateFlow<List<Map<String, Any>>>(emptyList())
    val categories: StateFlow<List<Map<String, Any>>> = _categories

    private val _products = MutableStateFlow<List<Map<String, Any>>>(emptyList())
    val products: StateFlow<List<Map<String, Any>>> = _products

    fun loadCategories() {
        viewModelScope.launch {
            val snapshot = firestore.collection("catalogue").get().await()
            _categories.value = snapshot.documents.mapNotNull { it.data }
        }
    }

    fun loadProductsByCategory(category: String) {
        viewModelScope.launch {
            val snapshot = firestore.collection("products")
                .whereEqualTo("category", category)
                .get()
                .await()
            _products.value = snapshot.documents.mapNotNull { it.data }
        }
    }
}