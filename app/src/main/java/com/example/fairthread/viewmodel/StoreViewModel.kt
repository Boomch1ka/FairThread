package com.example.fairthread.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fairthread.data.repository.FirestoreRepository
import com.example.fairthread.model.Product
import com.example.fairthread.model.Store
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

open class StoreViewModel(
    private val repo: FirestoreRepository = FirestoreRepository()
) : ViewModel() {

    val _store = MutableStateFlow<Store?>(null)
    val store: StateFlow<Store?> = _store

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    val _groupedProducts = MutableStateFlow<Map<String, List<Product>>>(emptyMap())
    val groupedProducts: StateFlow<Map<String, List<Product>>> = _groupedProducts

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _categories = MutableStateFlow<List<String>>(emptyList())
    val categories: StateFlow<List<String>> = _categories


    fun loadStoreCategories(storeId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val products = repo.getProductsByStore(storeId)
                val uniqueCategories = products.map { it.category }.distinct()
                _categories.value = uniqueCategories
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }


    fun loadStore(storeId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val storeData = repo.getStoreById(storeId)
                val allProducts = repo.getProductsByStore(storeId)
                val grouped = allProducts.groupBy { it.category }

                _store.value = storeData
                _products.value = allProducts
                _groupedProducts.value = grouped
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
}