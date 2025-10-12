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

class ProductViewModel(
    private val repo: FirestoreRepository = FirestoreRepository()
) : ViewModel() {

    private val _product = MutableStateFlow<Product?>(null)
    val product: StateFlow<Product?> = _product

    fun loadProduct(productId: String) {
        viewModelScope.launch {
            val products = NetworkModule.api.getProducts()
            _product.value = products.find { it.id == productId }
        }
    }

    fun addToCart(product: Product) {
        val uid = repo.getCurrentUserId() ?: return
        viewModelScope.launch {
            repo.addToCart(uid, product)
        }
    }
}
