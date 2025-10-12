package com.example.fairthread.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fairthread.data.api.FairThreadApi
import com.example.fairthread.di.NetworkModule
import com.example.fairthread.model.Category
import com.example.fairthread.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CatalogueViewModel(
    private val api: FairThreadApi = NetworkModule.api
) : ViewModel() {

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage


    fun loadCatalogue() {
        viewModelScope.launch {
            try {
                _categories.value = api.getCategories()
                _products.value = api.getProducts() // Load all products
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }

    fun filterProductsByCategory(categoryId: String) {
        viewModelScope.launch {
            try {
                val allProducts = api.getProducts()
                _products.value = allProducts.filter { product -> product.category == categoryId }
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }
}