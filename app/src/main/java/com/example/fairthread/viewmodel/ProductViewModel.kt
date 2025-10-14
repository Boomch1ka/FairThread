package com.example.fairthread.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fairthread.data.repository.FirestoreRepository
import com.example.fairthread.model.Product
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

open class ProductViewModel(
    private val repo: FirestoreRepository = FirestoreRepository()
) : ViewModel() {

    private val _product = MutableStateFlow<Product?>(null)
    val product: StateFlow<Product?> = _product
    val isLoading = MutableStateFlow(false)
    val errorMessage = MutableStateFlow<String?>(null)

    fun loadProduct(productId: String) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val snapshot = FirebaseFirestore.getInstance()
                    .collection("products")
                    .document(productId)
                    .get()
                    .await()

                val product = snapshot.toObject(Product::class.java)
                _product.value = product?.copy(id = snapshot.id)
                errorMessage.value = if (product == null) "Product not found" else null
            } catch (e: Exception) {
                errorMessage.value = "Failed to load product: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }

    fun addToCart(product: Product) {
        val uid = repo.getCurrentUserId() ?: return
        viewModelScope.launch {
            repo.addToCart(uid, product)
        }
    }
}