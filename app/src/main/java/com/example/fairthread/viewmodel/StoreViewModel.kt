package com.example.fairthread.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fairthread.di.NetworkModule
import com.example.fairthread.model.Product
import com.example.fairthread.model.Store
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StoreViewModel : ViewModel() {

    private val _store = MutableStateFlow<Store?>(null)
    val store: StateFlow<Store?> = _store

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    fun loadStore(storeId: String) {
        viewModelScope.launch {
            val allStores = NetworkModule.api.getStores()
            _store.value = allStores.find { it.id == storeId } as Store?

            val allProducts = NetworkModule.api.getProducts()
            _products.value = allProducts.filter { it.category == storeId }
        }
    }

//    fun loadProductsForStore(storeId: String) {
//        viewModelScope.launch {
//            try {
//                val snapshot = firestore.collection("products")
//                    .whereEqualTo("storeId", storeId)
//                    .get()
//                    .await()
//                _storeProducts.value = snapshot.documents.mapNotNull { it.data }
//            } catch (e: Exception) {
//                _storeProducts.value = emptyList()
//            }
//        }
//    }
}