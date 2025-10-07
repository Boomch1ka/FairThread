package com.example.fairthread.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ProductViewModel(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) : ViewModel() {

    private val _product = MutableStateFlow<Map<String, Any>?>(null)
    val product: StateFlow<Map<String, Any>?> = _product

    fun loadProduct(productId: String) {
        viewModelScope.launch {
            val snapshot = firestore.collection("products")
                .document(productId)
                .get()
                .await()
            _product.value = snapshot.data
        }
    }
}