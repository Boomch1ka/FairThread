package com.example.fairthread.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fairthread.data.repository.FirestoreRepository
import com.example.fairthread.di.NetworkModule
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
                val products = NetworkModule.api.getProducts()
                _product.value = products.find { it.id == productId }
                errorMessage.value = if (_product.value == null) "Product not found" else null
            } catch (e: Exception) {
                errorMessage.value = e.message
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
